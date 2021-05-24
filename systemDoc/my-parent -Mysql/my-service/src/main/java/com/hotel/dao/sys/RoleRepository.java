package com.hotel.dao.sys;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.sys.Role;

public interface RoleRepository extends BaseRepository<Role, String> {

	@Query(value="select r from UserRole ur,Role r where ur.roleId=r.id and ur.userId=:userId ")
	List<Role> queryRolesByUserId(@Param("userId") String userId);

	@Query(value="from Role a where a.deleted=0")
	Page<Role> queryAllUnDeleted(Pageable pageable);

	@Query(value="from Role a where a.deleted=0 and (a.name like CONCAT('%',:keyword,'%')  )")
	Page<Role> queryByCodeOrName(String keyword, Pageable pageable);

	Boolean existsByNameAndDeleted(String roleName, int i);

	Boolean existsByNameAndIdNotAndDeleted(String roleName, String roleId, int i);
	
}
