package com.rfq.service;

import com.rfq.entity.ServiceResult;
import com.rfq.entity.sys.SysRoler;

public interface BasicService {
	ServiceResult<SysRoler> getLoginUser();
}
