package com.hotel.dao.sys.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hotel.common.impl.BaseDaoImpl;
import com.hotel.dao.sys.MenuDao;
import com.hotel.entity.sys.Menu;
import com.hotel.util.DaoUtil;



@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl implements MenuDao {

	// 日志
	private Logger logger = LogManager.getLogger(MenuDaoImpl.class);

	@Override
	public List<Menu> queryMenu(String userId) {
		String sql = "SELECT m.* FROM sys_user u \r\n" + 
				"INNER JOIN sys_user_role hur ON u.id=hur.user_id \r\n" + 
				"INNER JOIN sys_role r ON hur.role_id=r.id AND r.canceled=0 \r\n" + 
				"INNER JOIN sys_role_permissions hrp ON hrp.role_id=r.id \r\n" + 
				"INNER JOIN sys_menu m ON hrp.menu_id=m.id AND m.state=0 \r\n" + 
				"WHERE u.id=? AND u.state=0 AND m.type<>2 GROUP BY m.id ORDER BY sort ASC ";
		List<Menu> menus = null;
		try {
			menus = super.jdbcTemplate.query(sql, new Object[] { userId }, new RowMapper<Menu>() {
				@Override
				public Menu mapRow(ResultSet rs, int i) throws SQLException {
					Menu menu = DaoUtil.setEntity(Menu.class, rs);
					return menu;
				}
			});
		} catch (DataAccessException e) {
			logger.warn(e);
		}
		return menus;
	}

	/**
	 * 菜单对应角色表
	 */
	@Override
	public List<Map<String, Object>> queryMenuOfRole(String hospitalId) {
		try {
			String sql = "SELECT m.id,m.name,m.url,m.pid,GROUP_CONCAT(hr.id SEPARATOR ',') roleids  \r\n" + 
					"FROM h_menu m \r\n" + 
					"INNER JOIN h_role_permissions hrp ON hrp.menuId=m.id AND hrp.HospitalId=? \r\n" + 
					"INNER JOIN h_role hr ON hrp.role_id=hr.id AND hr.canceled=1 \r\n" + 
					"WHERE m.state=0 AND m.type<>2 \r\n"+
				    "     AND IFNULL(m.version,0)&(SELECT IFNULL(VERSION,0) FROM h_hospital WHERE hospitalid=?)=(SELECT IFNULL(VERSION,0) FROM h_hospital WHERE hospitalid=?) \r\n" + 
					"GROUP BY m.id ORDER BY m._sort ASC  ";
			return super.queryBysqlList(sql, new Object[] { hospitalId,hospitalId,hospitalId });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
