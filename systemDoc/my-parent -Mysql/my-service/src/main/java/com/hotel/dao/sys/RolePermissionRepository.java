package com.hotel.dao.sys;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.sys.RolePermission;

public interface RolePermissionRepository extends BaseRepository<RolePermission, String>{

	void deleteByRoleId(String roleId);

	@Query(value="SELECT m.id FROM Role r INNER JOIN RolePermission rp ON rp.roleId=r.id INNER JOIN Menu m ON m.id=rp.menuId  where r.id=:roleId ")
	List<Integer> queryRoleMenuIds(String roleId);
 
}
