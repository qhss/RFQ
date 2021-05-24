package com.hotel.dao.sys;

import java.util.List;
import java.util.Map;

import com.hotel.common.BaseDaoInterface;
import com.hotel.entity.sys.Menu;
import com.hotel.entity.sys.Role;
import com.hotel.entity.sys.User;

/**
 *
 */
public interface UserDao extends BaseDaoInterface{

	
	/**
	 * 查询角色
	 * @param userId
	 * @return
	 */
	public abstract List<Role> queryRole(String userId);
	
	
	/**
	 * 查询权限
	 * @param roleId
	 * @return
	 */
	public abstract List<Menu> queryPermissions(Integer roleId);
	
	
	/**
	 * 删除所有用户角色对应数据
	 * @param userId
	 * @return
	 */
	public abstract Integer deleteUserRole(String userId);
	
	/**
	 *添加用户角色对应数据
	 * @param userId
	 * @return
	 */
	public abstract Integer addUserRole(String userId,Object[] roleId);
	
	
	
	/**
	 * 新增时查询用户名是否存在
	 * @param loginName
	 * @return
	 */
	public abstract Integer queryUserByLoginName(String loginName);
	
	/**
	 * 医生登录接口调用，验证登录用户账号的可用性
	 * @param loginName
	 * @return
	 */
	public abstract Integer queryNormalUserByLoginName(String loginName);
	
	

	/**
	 * 查询当前用户信息
	 * @param user
	 * @return
	 */
	public abstract User findCurrUser(User user);

	/**
	 * 根据登陆账号获取用户
	 * @param loginName
	 * @return
	 */
	public abstract User getUserByLoginName(String loginName);


	public abstract List<Map<String, Object>> queryRecipeDayCount();


	public abstract List<Map<String, Object>> queryRecipeCount();
	
	/**
	 *	验证token值
	 * @param token
	 * @return
	 */
	public abstract Integer isExitsToken(String token);
	
	/**
	 * 查找该token最新记录的过期时间
	 * @param token
	 * @return
	 */
	public abstract Map<String,Object> findLatestToken(String token);
	
	/**
	 * 删除失效token
	 */
	public abstract void deleteInvalidToken(String userId,String currentToken);

	/**
	 * 用户是否有某个功能项对应的权限
	 * @param memCode
	 * @return
	 */
	public abstract Integer IsHasRight(String memCode, String userId);

	/**
	 * 医馆新增时查询用户名是否存在
	 * @param loginName
	 * @return
	 */
	public abstract Integer queryUserByHosLoginName(String loginName);



	

	public Integer addUserRole(String id,  String HospitalId,Object[] roleId);

	
}
