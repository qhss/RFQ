package com.hotel.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hotel.dao.sys.RoleDao;
import com.hotel.dao.sys.UserDao;
import com.hotel.dao.sys.UserRepository;
import com.hotel.dao.sys.UserRoleRepository;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.Role;
import com.hotel.entity.sys.User;
import com.hotel.entity.sys.UserRole;
import com.hotel.service.BasicServiceImpl;
import com.hotel.service.sys.OrganizationService;
import com.hotel.service.sys.RoleService;
import com.hotel.service.sys.UserService;
import com.hotel.utils.Md5Encoder;
import com.hotel.utils.Util;



@Service("userService")
public class UserServiceImpl extends BasicServiceImpl implements UserService {
	
	private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Resource(name = "userDao")
	UserDao userDao;

	
	@Resource(name = "roleDao")
	RoleDao roleDao;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleService roleService;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	OrganizationService organizationService;
	
	@Override
	public Page<User> queryOnePage(String keyword ,String departId,String pId,Integer page, Integer row, Sort sort) {
		User user = getLoginUser().getData();
		if(pId.equals("0")){ //根目录,获得所有未被删除的用户
			if(StringUtils.isBlank(keyword)) {
				Page<User> list= userRepository.queryAllUnDeleted(user.getCompanyId(),PageRequest.of(page,row,sort));
				return list;
			}else {//非根目录,获得指定部门下未被删除的用户
				Page<User> list= userRepository.queryByCodeOrName(keyword,user.getCompanyId(),PageRequest.of(page,row,sort));
				return list;
			}
		}else{
			if(StringUtils.isBlank(keyword)) {
				Page<User> list= userRepository.queryAllUnDeleted(user.getCompanyId(),PageRequest.of(page,row,sort));
				return list;
			}else {
				Page<User> list= userRepository.queryByCodeOrName(keyword,user.getCompanyId(),PageRequest.of(page,row,sort));
				return list;
			}
		}
	}


	@Override
	public ServiceResult<Boolean> loginNameExist(String loginName) {
		Boolean isExist=userRepository.existsByLoginNameAndDeleted(loginName,0);
		return new ServiceResult<Boolean>(isExist);
	}


	@Override
	public ServiceResult<Boolean> loginNameExistExpectUserId(String loginName,String userId) {
		Boolean isExist=userRepository.existsByLoginNameAndIdNotAndDeleted(loginName,userId,0);
		return new ServiceResult<Boolean>(isExist);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceResult<Integer> addOrUpdate(String userId, String userName,String loginName,String password,
			Integer sex, String phone, String departId, Object[] roleIds) {
		try {
			User user=new User();
			if(!StringUtils.isBlank( userId)) {
				Optional<User> userOption= userRepository.findById(userId);
				if(userOption.isPresent())
					user=userOption.get();
				
				//现有用户,需要清除当前用户的角色数据
				userRoleRepository.removeByUserId(userId);
			}else {
				//新增用户(保存登录账号和密码)
				user.setLoginName(loginName);
				user.setRandomStr(Util.getRandom());
				user.setPassword(Md5Encoder.getMd5(password+ user.getRandomStr()));
			}
			user.setUserName(userName);
			user.setPhone(phone);
			user.setSex(sex);
			
			//设置公司ID
			//TODO 这里不对,需要根据部门找公司
			String companyId= getLoginUser().getData().getCompanyId();
			if(companyId==null)
				companyId="";
			user.setCompanyId(companyId);
			
			user.setDeleted(User.DELETE_STATE_UNDELETE);
			//设置部门id
			//user.setDepartId(departId);
			
			user.setCanceled(User.CANCEL_STATE_UNCENCEL);
			
			user=userRepository.save(user);
			
			//添加角色
			List<UserRole> userRoles=new ArrayList<>();
			for (Object roleid : roleIds) {
				if(roleid!=null && !StringUtils.isBlank(roleid.toString())) {
					UserRole userRole=new UserRole();
					//userRole.setCompanyId(companyId);
					userRole.setDeleted(UserRole.DELETE_STATE_UNDELETE);
					userRole.setUserId(user.getId());//用最新的userid,因为新增的情况
					userRole.setRoleId(roleid.toString());
					
					userRoles.add(userRole);
				}
			}
			if(userRoles.size()>0)
				userRoleRepository.saveAll(userRoles);
			
			return new ServiceResult<Integer>(1);
			
		}catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ServiceResult<Integer>(true,e.getMessage());
		}
	}
	
	
	@Override
	public ServiceResult<Integer> UpdateState(String id, int canceled) {
		try {
			if(StringUtils.isNotBlank(id)) {
				User user=userRepository.findById(id).get();
				
				if(user.getCanceled()!=canceled){
					user.setCanceled(canceled);
				}
				userRepository.save(user);
				
				return new ServiceResult<Integer>(1);
			}
			
			return new ServiceResult<Integer>(true,"参数不能不能为空");
			
		} catch (Exception e) {
			logger.error(e);
			return new ServiceResult<Integer>(true,e.getMessage());
		}
	}

	@Override
	public ServiceResult<Integer> Delete(String id) {
		try {
			if(StringUtils.isNotBlank(id)) {
				User user=userRepository.findById(id).get();
				
				//TODO 增加删除校验
				user.setDeleted(User.DELETE_STATE_DELETED);
				userRepository.save(user);
				
				return new ServiceResult<Integer>(1);
			}
			
			return new ServiceResult<Integer>(true,"参数不能不能为空");
			
		} catch (Exception e) {
			logger.error(e);
			return new ServiceResult<Integer>(true,e.getMessage());
		}
	}

	@Override
	public ServiceResult<Integer> batchDelete(String[] ids) {
		try {
			String result="";
			for (String id : ids) {
				if(id!=null&&!StringUtils.isBlank(id)){
					result+=(Delete(id).getErrorMessage());
				}
			}
			if(!StringUtils.isBlank(result))
				return new ServiceResult<Integer>(true,"数据未能完全删除");
			
			return new ServiceResult<Integer>(1);
		}catch (Exception e) {
			return new ServiceResult<Integer>(true,e.getMessage());
		}
		
	}

	
	@Override
	public ServiceResult<User> findById(String userId) {
		Optional<User> userOption= userRepository.findById(userId);
		ServiceResult<List<Role>> userRoles= roleService.queryRolesByUserId(userId);
		if(userOption.isPresent()) {
			User user=userOption.get();
			if(userRoles.isSuccess())
				user.setRoles(userRoles.getData());
			return new ServiceResult<User>(user);
		}
		return null;
	}

	@Override
	public ServiceResult<User> queryUserByLoginName(String loginName) {
		
		Optional<User> user= userRepository.findByLoginNameAndCanceled(loginName,User.CANCEL_STATE_UNCENCEL);
		if(user.isPresent())
			return new ServiceResult<User>(user.get());
		else
			return null;
		
	}

	@Override
	public ServiceResult<User> checkLogin(String loginName, String password) {
		ServiceResult<User> optionUser= queryUserByLoginName(loginName);
		if(optionUser!=null) {
			if(optionUser.isSuccess()) {
				User user=optionUser.getData();
				String expectPassword=Md5Encoder.getMd5(password+user.getRandomStr());
				boolean ok= expectPassword.equals(user.getPassword());  
				if(ok)
					return new ServiceResult<User>(user);
			}
		}
		return null;
	}


	@Override
	public String updateUser(User user) {
		Optional<User> oldUser = userRepository.findById(user.getId());
		if(oldUser.isPresent()) {
			User updateUser=oldUser.get();
			if (null != user.getPassword() && !"".equals(user.getPassword())) {
				updateUser.setPassword(Md5Encoder.getMd5(user.getPassword() + updateUser.getRandomStr()));
				updateUser.setIsUpdatePassword(0);
			}
			updateUser.setSex(user.getSex());
			updateUser.setUserName(user.getUserName());
			updateUser.setPhone(user.getPhone());
			
			updateUser=userRepository.save(updateUser);
			return updateUser.getId();
			
		}else {
			return null;
		}
	}


}
