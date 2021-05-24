package com.hotel.service.sys.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hotel.common.impl.BaseServiceImpl;
import com.hotel.dao.sys.LogDao;
import com.hotel.entity.sys.Log;
import com.hotel.service.sys.LogServiceInterface;
import com.hotel.utils.DateTime;


/**
 * 日志
 */
@Service("logService")
public class LogServiceImpl extends BaseServiceImpl implements LogServiceInterface {
	// 日志
	private Logger logger = LogManager.getLogger(LogServiceImpl.class);
		
	@Resource(name = "logDao")
	LogDao logDao;

	@Override
	public Map<String, Object> searchLog(Integer page, Integer row,String startTime,String endTime) {
		return logDao.searchLog(page,row,startTime,endTime);
	}

	@Override
	public void addJsErrorLog(String msg, String url, String line, String userAgent) {
		try {
			Log log = new Log();
			log.setModular("前端异常");
			log.setContent("脚本错误");
			log.setDetails("异常信息:"+msg+",url:"+url+","+",错误行号:"+line+",浏览器版本:"+userAgent);
			log.setCreateTime(DateTime.getNowTimestampOfLong());
			log.setCreateUserId(GetLoginUser().getId());
			log.setState(1);
			log.setType("脚本错误");
			logDao.addEntityUUID(log);
		}catch(Exception ex) {
			logger.error(ex);
		}
	}

}
