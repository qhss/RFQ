package com.rfq.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rfq.common.impl.BaseDaoImpl;
import com.rfq.dao.sys.LogDao;



@Repository("logDao")
public class LogDaoImpl extends BaseDaoImpl implements LogDao {

	private Logger logger = LogManager.getLogger(LogDao.class);
	
	@Override
	public Map<String,Object> searchLog(Integer page, Integer row,String startTime,String endTime){
		
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			
			//User user = GetLoginUser();
			
			String sql = "select l.id,l.modular,l.content,u.userName,l.ip,l.type,l.details,l.creatTime	"
					+ " from h_log l "
					+ "inner join h_user u on u.id = l.userId "
					+ "where l.type = '脚本错误' ";
			
			if (!StringUtils.isEmpty(startTime)) {
				sql += " and FROM_UNIXTIME(l.creatTime, '%Y-%m-%d')  >= '"
						+ startTime + "'";
			}
			
			if (!StringUtils.isEmpty(endTime)) {
				sql += " and  FROM_UNIXTIME(l.creatTime, '%Y-%m-%d')  <= '"
						+ endTime + "'";
			}
			
			int count = super.queryBysqlCount("select count(1) from ("+sql+") f", null); 
			map.put("total", count);
			
			sql += " order by l.creatTime desc limit ?,?";
			List<Map<String,Object>> list = super.queryBysqlList(sql, new Object[] {(page-1),row});
			map.put("data", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
		
		return map;
	}
	
}
