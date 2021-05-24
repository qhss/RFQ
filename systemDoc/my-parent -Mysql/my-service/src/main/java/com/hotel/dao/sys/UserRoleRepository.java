package com.hotel.dao.sys;

import java.util.List;
import java.util.Optional;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.sys.UserRole;

public interface UserRoleRepository extends BaseRepository<UserRole, String> {

	Optional<List<UserRole>> findByUserId(String userId);

	void deleteByUserId(String userId);

	void removeByUserId(String userId);

	void deleteByRoleId(String roleId);

}
