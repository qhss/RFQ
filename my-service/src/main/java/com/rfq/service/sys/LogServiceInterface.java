package com.rfq.service.sys;

import java.util.Map;


/**
 * 
 * @author 
 * 日志
 */
public interface LogServiceInterface {

	/**
	 * 查询日志
	 * @param log
	 * @param page
	 * @param row
	 * @return
	 */
	public abstract Map<String, Object> searchLog(Integer page,Integer row,String startTime,String endTime);

	/**
	 * 保存Js日志
	 * @param msg
	 * @param url
	 * @param line
	 * @param userAgent
	 */
	public abstract void addJsErrorLog(String msg, String url, String line, String userAgent);
}
