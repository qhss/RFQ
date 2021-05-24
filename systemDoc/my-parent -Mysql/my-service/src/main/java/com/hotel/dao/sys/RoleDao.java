package com.hotel.dao.sys;

import java.util.List;

import com.hotel.common.BaseDaoInterface;
import com.hotel.entity.sys.Menu;


/**
 * 
 * @author 
 *
 */
public interface RoleDao extends BaseDaoInterface{

	List<Menu> queryAllMenus();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 根据角色名查询
	 * @param roleName
	 * @return 0不存在 1存在
	 */
	public abstract Integer queryByName(String roleName);
	
	

	
	/**
	 * 查询用户所拥有的权限
	 */
	public abstract List<Integer> queryPermissionsByRole(String roleId);
	
	
	/**
	 * 修改权限
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	public Integer updatePermissions(Integer roleId, Object[] menuIds);
	
	/**
	 * 添加权限
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	public Integer addPermissions(String roleId,String companyId, Object[] menuIds);
	
	/**
	 * 删除权限
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	public Integer deletePermissions(String roleId);


	
	
}
