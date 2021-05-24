package com.hotel.service.sys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.User;
import com.hotel.service.BasicService;



public interface UserService extends BasicService{
	
	
	/**
	 * 查询所有用户 
	 * @param user
	 * @return
	 */
	//public abstract List<User> queryUserList(User user);
	

	/**
	 * 按名称模糊查询用户
	 * @param strName
	 * @return
	 */
	//public List<Map<String, Object>> queryUserByName(String strName);
	
	/**
	 * 根据登陆名称查询用户
	 * @param user
	 * @return
	 */
	//public abstract User queryUserForLoginName(User user);
	
	
	/**
	 * 修改或新增
	 * @param user
	 * @return
	 */
	//public abstract Object addOrUpdate(User user,Object[] roleId);
	
	/**
	 * 修改个人资料
	 * @param user
	 * @return
	 */
	public abstract String updateUser(User user);
	
	
	/**
	 * 根据用户ID查询用户详细信息
	 * @param userId
	 * @return
	 */
	//public abstract User queryUserById(String userId);
	
	
	/**
	 * 新增时查询用户名是否存在
	 * @param loginName
	 * @return
	 */
	public abstract ServiceResult<User> queryUserByLoginName(String loginName);
	
	/**
	 * @param loginName
	 * @return
	 */
	//public abstract Integer queryNormalUserByLoginName(String loginName);
	
	//授权用户验证时获取用户信息
	//public abstract User queryAuthUserForLoginName(User u);
	
	
	/**
	 * 获取用户
	 * @return
	 */
	//public abstract User findCurrUser();

	
	

	/**
	 * 当前用户是否有某个功能的权限
	 * @param memCode
	 * @return
	 */
	//public Integer IsHasRight(String memCode);


	/**
	 * 指定用户是否有某个功能对应的权限
	 * @return
	 */
	//Integer IsHasRight(String memCode, String userId);


	public abstract ServiceResult<User> checkLogin(String loginName, String password);


	ServiceResult<User> findById(String id);


	Page<User> queryOnePage(String keyword,String departId,String pId, Integer page, Integer row, Sort sort);


	public abstract ServiceResult<Boolean> loginNameExist(String loginName);


	public abstract ServiceResult<Boolean> loginNameExistExpectUserId(String loginName,String userId);


	public abstract ServiceResult<Integer> addOrUpdate(String id, String userName, String loginName, String password, Integer sex, String phone, String departId, Object[] roleId);


	public abstract ServiceResult<Integer> UpdateState(String id, int state);


	public abstract ServiceResult<Integer> Delete(String id);


	public abstract ServiceResult<Integer> batchDelete(String[] ids);





	

	
	
	
}
