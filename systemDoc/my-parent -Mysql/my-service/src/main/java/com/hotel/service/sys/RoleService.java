package com.hotel.service.sys;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.hotel.common.BaseServiceInterface;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.Menu;
import com.hotel.entity.sys.Role;

/**
 * 角色
 * @author duanqs
 *
 */
public interface RoleService extends BaseServiceInterface {

	List<Menu> queryAllMenus();



	/**
	 * 查询用户所拥有的权限
	 */
	public abstract List<Integer> queryPermissionsByRole(String roleId);

	/**
	 * 更新角色所对应的权限
	 * 
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	public ServiceResult<Integer> updatePermissions(String roleId, Object[] menuIds);




	public abstract List<Role> findAll();



	public abstract ServiceResult<List<Role>> queryRolesByUserId(String userId);



	public abstract Page<Role> queryOnePage(String keyword, Integer pageIndex, Integer pageRow, Sort sort);




	ServiceResult<Role> findById(String roleId);




	ServiceResult<Boolean> roleNameExist(String roleName);




	ServiceResult<Boolean> roleNameExistExpectRoleId(String roleName, String roleId);




	ServiceResult<String> save(String id, String name, String description);



	ServiceResult<Integer> UpdateState(String id, int state);



	ServiceResult<Integer> Delete(String id);



	ServiceResult<Integer> batchDelete(String[] ids);



	//public abstract ServiceResult<List<Role>> queryRoleByUserId(String id);
}
