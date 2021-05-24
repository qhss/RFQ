package com.hotel.dao.sys;

import java.util.Map;

import com.hotel.common.BaseDaoInterface;

public interface LogDao extends BaseDaoInterface {
	
	/**
	 * 查询日志
	 * @param log
	 * @param page
	 * @param row
	 * @return
	 */
	Map<String,Object> searchLog(Integer page, Integer row,String startTime,String endTime);
	
}
