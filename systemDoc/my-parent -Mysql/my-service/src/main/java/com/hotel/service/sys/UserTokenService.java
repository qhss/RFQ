package com.hotel.service.sys;

import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.UserToken;
import com.hotel.service.BasicService;

public interface UserTokenService extends BasicService {

	ServiceResult<UserToken> findByToken(String token);

	ServiceResult<UserToken> findByTokenAndUserId(String token, String userId);

	ServiceResult<UserToken> createToken(String userId);

	String queryUserId(String token);

	ServiceResult<UserToken> findById(String id);

}
