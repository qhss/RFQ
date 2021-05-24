package com.hotel.dao.sys.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hotel.common.impl.BaseDaoImpl;
import com.hotel.dao.sys.RoleDao;
import com.hotel.entity.sys.Menu;
import com.hotel.utils.Util;


/**
 * 
 * @author Administrator
 * 
 */
@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {

	// 日志
	private Logger logger = LogManager.getLogger(RoleDaoImpl.class);

	@Override
	public List<Menu> queryAllMenus() {
		String sql = "SELECT id,NAME,pid \r\n" + 
				"FROM sys_menu \r\n" + 
				"WHERE  state=0 \r\n" + 
				"ORDER BY sort ";
		final List<Menu> menus = new ArrayList<Menu>();
		try {
			super.jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Menu menu = new Menu();
					menu.setId(rs.getInt("id"));
					menu.setName(rs.getString("name"));
					menu.setPid(rs.getInt("pid"));
					menus.add(menu);
				}
			});
		} catch (DataAccessException e) {
			logger.warn(e);
		}
		return menus;
	}
	
	
	
	
	
	
	
	
	public Integer queryByName(String roleName) {
		String sql = "select count(1) from h_role where name=?";
		Integer i = 0;
		try {
			i = super.jdbcTemplate.queryForObject(sql, new Object[] { roleName }, Integer.class);
		} catch (DataAccessException e) {
			logger.warn(e);
		}
		return i;
	}

	

	@Override
	public List<Integer> queryPermissionsByRole(String roleId) {
		String sql = "SELECT m.id FROM sys_role r INNER JOIN sys_role_permissions rp ON rp.role_id=r.id INNER JOIN sys_menu m ON m.id=rp.`menu_id`  where r.id=?";
		List<Integer> menuId = null;
		try {
			menuId = super.jdbcTemplate.query(sql, new Object[] { roleId }, new RowMapper<Integer>() {
				@Override
				public Integer mapRow(ResultSet rs, int i) throws SQLException {
					Integer id = rs.getInt("id");
					return id;
				}
			});
		} catch (DataAccessException e) {
			logger.warn(e);
		}
		return menuId;
	}

	@Override
	public Integer updatePermissions(Integer roleId, Object[] menuIds) {
		return null;
	}

	@Override
	public Integer deletePermissions(String roleId) {
		String sql = "delete from sys_role_permissions where role_id=?";
		int i = 0;
		try {
			i = super.jdbcTemplate.update(sql, new Object[] { roleId });
		} catch (DataAccessException e) {
			logger.error(e);
			throw e;
		}
		return i;
	}

	@Override
	public Integer addPermissions(final String roleId,final String companyId, final Object[] menuIds) {
		String sql = "replace into sys_role_permissions(role_id,menu_id,company_id)values(?,?,?)";
		int[] i = { 0 };
		try {
			i = super.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, roleId);
					ps.setObject(2, menuIds[i]);
					ps.setObject(3, companyId);
				}

				@Override
				public int getBatchSize() {
					return menuIds.length;
				}
			});
		} catch (DataAccessException e) {
			logger.error(e);
			throw e;
		}
		return Util.toInt(i);
	}

	
}
