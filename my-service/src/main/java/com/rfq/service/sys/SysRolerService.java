package com.rfq.service.sys;

import com.rfq.entity.sys.SysRoler;

import com.rfq.entity.ServiceResult;
import com.rfq.service.BasicService;



public interface SysRolerService extends BasicService{
	

	
	/**
	 * 新增时查询用户名是否存在
	 * @param loginName
	 * @return
	 */
	public abstract ServiceResult<SysRoler> queryUserByLoginId(String loginName);





}
