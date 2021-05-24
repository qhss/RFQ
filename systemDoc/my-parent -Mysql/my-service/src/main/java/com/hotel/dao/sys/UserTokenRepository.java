package com.hotel.dao.sys;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.sys.UserToken;

public interface UserTokenRepository extends BaseRepository<UserToken, String> {

	Optional<UserToken> findByTokenAndUserId(String token, String userId);

	Optional<UserToken> findByToken(String token);

	@Query(value="select a.userId from UserToken a where a.token=:token ")
	String queryUserId(@Param("token") String token);

}
