package com.hotel.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.common.impl.BaseServiceImpl;
import com.hotel.dao.sys.MenuRepository;
import com.hotel.dao.sys.RolePermissionRepository;
import com.hotel.dao.sys.RoleRepository;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.Menu;
import com.hotel.entity.sys.Role;
import com.hotel.entity.sys.RolePermission;
import com.hotel.service.sys.RoleService;


/**
 * 
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService{
	
	private static Logger logger = LogManager.getLogger(RoleServiceImpl.class);
	
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	RolePermissionRepository rolePermissionRepository;
	
	@Autowired
	MenuRepository menuRepository;
	
//	@Resource(name = "roleDao")
//	RoleDao roleDao;

	
	/**
	 * 分页查询未删除的角色
	 */
	@Override
	public Page<Role> queryOnePage(String keyword ,Integer page, Integer row, Sort sort) {
		if(StringUtils.isBlank(keyword)) {
			Page<Role> list= roleRepository.queryAllUnDeleted(PageRequest.of(page,row,sort));
			return list;
		}
		else {
			Page<Role> list= roleRepository.queryByCodeOrName(keyword,PageRequest.of(page,row,sort));
			return list;
		}
	
	}

	/**
	 * 通过id查询对象,查不到返回null
	 * @param id
	 * @return
	 */
	@Override
	public ServiceResult<Role> findById(String id) {
		Optional<Role> result=roleRepository.findById(id);
		if(result.isPresent())
			if(result.get().getDeleted()==Role.DELETE_STATE_UNDELETE)
				return new ServiceResult<Role>(result.get());
			else
				return null;
		else
			return null;
	}
	
	/**
	 * 查询所有角色
	 */
	@Override
	public List<Role> findAll() {
		List<Role> list=roleRepository.findAll();
		return list;
	}
	
	
	/***
	 * 根据用户id获取角色列表
	 */
	@Override
	public ServiceResult<List<Role>> queryRolesByUserId(String userId) {
		List<Role> list=roleRepository.queryRolesByUserId(userId);
		return new ServiceResult<List<Role>>(list);
	}

	/**
	 * 通过角色查询所有菜单id
	 */
	@Override
	public List<Integer> queryPermissionsByRole(String roleId) {
		
		List<Integer> menuIds= rolePermissionRepository.queryRoleMenuIds(roleId);
		return menuIds;
	}
	
	/**
	 * 修改角色权限
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceResult<Integer> updatePermissions(String roleId, Object[] menuIds) {
		String companyId= GetLoginUser().getCompanyId();
		//先删除这个角色的权限
		rolePermissionRepository.deleteByRoleId(roleId);
		//新增权限
		List<RolePermission> list=new ArrayList<>();
		for (Object menuId : menuIds) {
			if(menuId!=null && !StringUtils.isBlank(menuId.toString())) {
				RolePermission rolePermission=new RolePermission();
				rolePermission.setRoleId(roleId);
				rolePermission.setMenuId(menuId.toString());
				rolePermission.setCompanyId(companyId);
				
				list.add(rolePermission);
			}
		}
		
		if(list.size()>0)
			rolePermissionRepository.saveAll(list);
		
		return new ServiceResult<Integer>(1);
	}
	
	/**
	 * 查询所有可选菜单
	 */
	@Override
	public  List<Menu> queryAllMenus() {
		return menuRepository.findAll();
	}
	

	@Override
	public ServiceResult<Boolean> roleNameExist(String roleName) {
		Boolean isExist=roleRepository.existsByNameAndDeleted(roleName,0);
		return new ServiceResult<Boolean>(isExist);
	}

	@Override
	public ServiceResult<Boolean> roleNameExistExpectRoleId(String roleName, String roleId) {
		Boolean isExist=roleRepository.existsByNameAndIdNotAndDeleted(roleName,roleId,0);
		return new ServiceResult<Boolean>(isExist);
	}

	/***
	 * 保存并返回id
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceResult<String> save(String id, String name, String descripe) {
		String companyId=GetLoginUser().getCompanyId();
		
		Role role=new Role();
		if(!StringUtils.isBlank(id)) {
			Optional<Role> roleOption=roleRepository.findById(id);
			if(roleOption.isPresent())
				role=roleOption.get();
		}
		
		role.setName(name);
		role.setDescripe(descripe);
		role.setCompanyId(companyId);
		role.setCanceled(Role.CANCEL_STATE_UNCENCEL);
		role.setType(Role.ROLE_TYPE_NOTMAL);
		role.setIsAdmin(Role.ROLE_NOT_ADMIN);
		role.setDeleted(Role.DELETE_STATE_UNDELETE);
		
		role=roleRepository.save(role);
		
		return new ServiceResult<>(role.getId());
	}

	/**
	 * 更新状态
	 */
	@Override
	public ServiceResult<Integer> UpdateState(String id, int canceled) {
		try {
			if(StringUtils.isNotBlank(id)) {
				Role user=roleRepository.findById(id).get();
				
				if(user.getCanceled()!=canceled){
					user.setCanceled(canceled);
				}
				roleRepository.save(user);
				
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
				Role role=roleRepository.findById(id).get();
				
				//TODO 增加删除校验
				role.setDeleted(Role.DELETE_STATE_DELETED);
				roleRepository.save(role);
				
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


	
	

//	@Override
//	public ServiceResult<List<Role>> queryRoleByUserId(String userId) {
//		Optional<List<Role>> optional=roleRepository.findByUserId(userId);
//		if(optional.isPresent())
//			return new ServiceResult<List<Role>>(optional.get());
//		else
//			return new ServiceResult<List<Role>>(true,"获取用户角色出错");
//	}

	
	
}
