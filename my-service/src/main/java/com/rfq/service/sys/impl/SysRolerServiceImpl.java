package com.rfq.service.sys.impl;

import com.rfq.dao.sys.SysRolerRepository;
import com.rfq.entity.ServiceResult;

import com.rfq.entity.sys.SysRoler;
import com.rfq.service.BasicServiceImpl;

import com.rfq.service.sys.SysRolerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;


@Service("userService")
public class SysRolerServiceImpl extends BasicServiceImpl implements SysRolerService {
	
	private static Logger logger = LogManager.getLogger(SysRolerServiceImpl.class);

	@Resource
	SysRolerRepository sysRolerRepository;


	@Override
	public ServiceResult<SysRoler> queryUserByLoginId(String loginId) {
		
		Optional<SysRoler> user= sysRolerRepository.findByLoginIdAndStatus(loginId, SysRoler.CANCEL_STATE_UNCENCEL);
		if(user.isPresent())
			return new ServiceResult<SysRoler>(user.get());
		else
			return null;
		
	}


}
