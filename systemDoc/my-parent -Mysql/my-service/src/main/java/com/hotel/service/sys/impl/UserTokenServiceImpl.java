package com.hotel.service.sys.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.sys.UserTokenRepository;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.UserToken;
import com.hotel.service.BasicServiceImpl;
import com.hotel.service.sys.UserTokenService;
import com.hotel.utils.DateTime;

@Service("userTokenService")
public class UserTokenServiceImpl extends BasicServiceImpl implements UserTokenService {

	//private static final Logger logger = LoggerFactory.getLogger(UserTokenServiceImpl.class);
	
	/**Token 默认过期时间**/
	public static int TOKEN_OVER_DAYS=30;
	
	@Autowired
	private UserTokenRepository userTokenRepository;

	
	@Override
	public ServiceResult<UserToken> findById(String id) {
		
		Optional<UserToken> data=userTokenRepository.findById(id);
		if(data.isPresent())
			return new ServiceResult<UserToken>(data.get());
		else
			return null;

	}

	@Override
	public ServiceResult<UserToken> findByTokenAndUserId(String token,String userId) {
		
		Optional<UserToken> data=userTokenRepository.findByTokenAndUserId(token,userId);
		if(data.isPresent())
			return new ServiceResult<UserToken>(data.get());
		else
			return null;

	}
	
	@Override
	public ServiceResult<UserToken> findByToken(String token) {
		
		Optional<UserToken> data=userTokenRepository.findByToken(token);
		if(data.isPresent())
			return new ServiceResult<UserToken>(data.get());
		else
			return null;

	}
	
	/**
	 * 创建用户Token,有效期30天
	 */
	@Override
	public ServiceResult<UserToken> createToken(String userId) {
		
		UserToken token=new UserToken();
		token.setUserId(userId);
		String newtoken=UUID.randomUUID().toString().replaceAll("-", "");
		token.setToken(newtoken);
		long overTime=DateTime.add(new Date(), TOKEN_OVER_DAYS).getTime();
		token.setOverTime(overTime);
		token.setCompanyId("");
		
//		token.setCreateTime(0L);
//		token.setCreateUserId("");
//		token.setDeleted(0);
//		token.setUpdateTime(0L);
//		token.setUpdateUserId("");
	
		UserToken data=userTokenRepository.save(token);
		
		return new ServiceResult<UserToken>(data);
	}

	@Override
	public String queryUserId(String token) {
//		String userid= userTokenRepository.queryUserId(token);
//		return userid;
		return "111";
	}
}
