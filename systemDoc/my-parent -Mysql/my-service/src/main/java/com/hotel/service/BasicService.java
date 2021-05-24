package com.hotel.service;

import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.User;

public interface BasicService {
	ServiceResult<User> getLoginUser();
}
