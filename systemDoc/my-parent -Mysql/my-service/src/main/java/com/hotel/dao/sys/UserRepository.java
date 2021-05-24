package com.hotel.dao.sys;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.sys.User;


public interface UserRepository extends BaseRepository<User, String> {

	Optional<User> findByLoginNameAndCanceled(String loginName, int sTATE_NORMAL);

	@Query(value="from User a where a.deleted=0 and a.companyId = :companyId ")
	Page<User> queryAllUnDeleted(String companyId,Pageable pageable);


	@Query(value="from User a where a.deleted=0 and a.companyId = :companyId " +
			"and (a.loginName like CONCAT('%',:keyword,'%') or a.userName like CONCAT('%',:keyword,'%') or a.phone like CONCAT('%',:keyword,'%') )")
	Page<User> queryByCodeOrName(String keyword,String companyId, Pageable pageable);


	Boolean existsByLoginNameAndDeleted(String loginName, int deleted);

	Boolean existsByLoginNameAndIdNotAndDeleted(String loginName, String userId, int deleted);


}
