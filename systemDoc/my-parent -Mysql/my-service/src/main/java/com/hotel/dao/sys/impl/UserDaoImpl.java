package com.hotel.dao.sys.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hotel.common.impl.BaseDaoImpl;
import com.hotel.dao.sys.UserDao;
import com.hotel.entity.sys.Menu;
import com.hotel.entity.sys.Role;
import com.hotel.entity.sys.User;
import com.hotel.util.DaoUtil;
import com.hotel.utils.Util;



@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	// 日志
	private Logger logger = LogManager.getLogger(UserDaoImpl.class);

	@Override
	public List<Role> queryRole(String userId) {
		String sql = "select r.* from h_user_role ur inner join h_user u on u.id=ur.user_id  INNER JOIN h_role r on r.id=ur.role_id where u.id=?";
		List<Role> roles = null;
		try {
			roles = super.jdbcTemplate.query(sql, new Object[] { userId }, new RowMapper<Role>() {
				@Override
				public Role mapRow(ResultSet rs, int i) throws SQLException {
					Role role = DaoUtil.setEntity(Role.class, rs);
					return role;
				}
			});
		} catch (DataAccessException e) {
			logger.warn(e);
		}
		return roles;
	}

	@Override
	public List<Menu> queryPermissions(Integer roleId) {
		String sql = "SELECT m.* \r\n" + 
				"FROM h_role r \r\n" + 
				"INNER JOIN h_role_permissions p ON p.role_id=r.id \r\n" + 
				"INNER JOIN h_menu m ON m.id=p.menuId \r\n" + 
				"WHERE  IFNULL(m.`version`,0)&IFNULL(h.`version`,0)=IFNULL(h.`version`,0) \r\n" + 
				"	AND r.id=? ";
		List<Menu> menus = null;
		try {
			menus = super.jdbcTemplate.query(sql, new Object[] { roleId }, new RowMapper<Menu>() {
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

	@Override
	public Integer deleteUserRole(String userId) {
		String sql = "delete from h_user_role where user_id=?";
		Integer i = 0;
		try {
			i = super.jdbcTemplate.update(sql, new Object[] { userId });
		} catch (DataAccessException e) {
			logger.error(e);
			throw e;
		}
		return i;
	}

	@Override
	public Integer addUserRole(final String userId, final Object[] roleId) {
		String sql = "insert into h_user_role(user_id,role_id,hospitalId) values(?,?,?)";

		int[] count = { 0 };
		try {
			count = super.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, userId);
					ps.setObject(2, roleId[i]);
				}

				@Override
				public int getBatchSize() {
					return roleId.length;
				}
			});
		} catch (DataAccessException e) {
			logger.error(e);
			throw e;
		}
		return Util.toInt(count);
	}

	@Override
	public Integer queryUserByLoginName(String loginName) {
		String sql = "select count(1) from h_user where loginName=? and state<>2";
		Integer i = 0;
		try {
			i = super.jdbcTemplate.queryForObject(sql, new Object[] { loginName }, Integer.class);
		} catch (DataAccessException e) {
			logger.warn(e);
		}
		return i;
	}

	@Override
	public Integer queryNormalUserByLoginName(String loginName) {
		String sql = "select count(1) from h_user where loginName=? and state=0";
		Integer i = 0;
		try {
			i = super.jdbcTemplate.queryForObject(sql, new Object[] { loginName }, Integer.class);
		} catch (DataAccessException e) {
			logger.warn(e);
		}
		return i;
	}

	@Override
	public User getUserByLoginName(String loginName) {
		String sql = "select id,loginName,isUpdatePassword,userName,phone,hospitalId,doctorId,state, "
				+ " sex,createTime,openId,password,randomStr from h_user where loginName=? and state<>2";
		User user = null;
		try {
			List<User> userList = jdbcTemplate.query(sql, new Object[] { loginName },
					new BeanPropertyRowMapper<User>(User.class));
			if (null != userList && userList.size() > 0) {
				user = userList.get(0);
			}
		} catch (DataAccessException e) {
			logger.warn(e);
		}
		return user;
	}

	
	@Override
	public User findCurrUser(User user) {
		User newUser = super.queryById(User.class, user.getId(), false);
		return newUser;
	}

	@Override
	public List<Map<String, Object>> queryRecipeDayCount() {
		User loginUser = this.GetLoginUser();
		String doctorId = loginUser.getId();
		if (doctorId == null) {
			return null;
		}
		String sql = "select FROM_UNIXTIME(createTime, '%d') AS day,  count(distinct patientId) as count "
				+ "from h_recipe_record where doctorId = ? and FROM_UNIXTIME(createTime, '%Y-%m')=DATE_FORMAT(CurDate(),'%Y-%m')  "
				+ "group by FROM_UNIXTIME(createTime, '%Y-%m-%d')";
		Object[] params = { doctorId };
		List<Map<String, Object>> list = super.queryBysqlList(sql, params);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryRecipeCount() {
		User loginUser = this.GetLoginUser();
		String doctorId = loginUser.getId();
		if (doctorId == null) {
			return null;
		}
		String sql = "select FROM_UNIXTIME(createTime, '%m') AS MONTH,  count(distinct patientId) as count "
				+ "from h_recipe_record where doctorId = ? and FROM_UNIXTIME(createTime, '%Y')=YEAR(CurDate())  "
				+ "group by FROM_UNIXTIME(createTime, '%Y-%m')";
		Object[] params = { doctorId };
		List<Map<String, Object>> list = super.queryBysqlList(sql, params);
		return list;
	}

	@Override
	public Integer isExitsToken(String token) {
		int i = 0;
		try {
			String sql = "select count(1) from h_token where token = ?";
			i = super.queryBysqlCount(sql, new Object[] { token });
		} catch (DataAccessException e) {
			logger.error(e);
		}
		return i;
	}

	@Override
	public Map<String, Object> findLatestToken(String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String sql = "select id,token,overDate,mobile,userId,hospitalId from h_token where token = ? order by createDate desc limit 1";
			map = super.queryBysqlMap(sql, new Object[] { token });
		} catch (DataAccessException e) {
			logger.error(e);
		}
		return map;
	}

	@Override
	public void deleteInvalidToken(String userId, String currentToken) {
		try {
			int deleteIndex = 0;
			if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(currentToken)) {
				String sql = "delete from h_token where userId = ? and token != ?";
				deleteIndex = super.delete(sql, new Object[] { userId, currentToken });
			} else if (!StringUtils.isEmpty(currentToken)) {
				String sql = "delete from h_token where token = ?";
				deleteIndex = super.delete(sql, new Object[] { currentToken });
			}
			System.out.println("删除: " + deleteIndex);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 查询用户是否有功能项对应的权限
	 */
	@Override
	public Integer IsHasRight(String memCode,  String userId) {
		int i = 0;
		try {
			String sql = "SELECT COUNT(m.code) FROM h_user u  " + "	INNER JOIN h_user_role hur ON u.id=hur.user_id  "
					+ "	INNER JOIN h_role r ON hur.role_id=r.id AND r.canceled=1  "
					+ "	INNER JOIN h_role_permissions hrp ON hrp.role_id=r.id   "
					+ "	INNER JOIN h_menu m ON hrp.menuId=m.id AND m.state=0  "
					+ "WHERE u.state=0 AND m.type=2 AND m.code=?  " + "	AND u.id=? ";
			i = super.queryBysqlCount(sql, new Object[] {  memCode, userId });
			if (i > 0)
				return 1;
			else
				return 0;
		} catch (DataAccessException e) {
			logger.error(e);
		}
		return i;
	}

	@Override
	public Integer queryUserByHosLoginName(String loginName) {
		String sql = "select count(1) from h_user where loginName=? and state<>2 ";
		Integer i = 0;
		try {
			i = super.jdbcTemplate.queryForObject(sql, new Object[] { loginName }, Integer.class);
		} catch (DataAccessException e) {
			logger.warn(e);
		}
		return i;
	}

	

	public Integer isExist(String medicineName, String conflictMedicineName) {
		try {
			return super.queryBysqlCount(
					" select count(1) from h_conflict_medicine where ((medicineName=? and conflictMedicineName=?) "
							+ " or (conflictMedicineName=? and medicineName=?))  ",
					new Object[] { medicineName, conflictMedicineName, medicineName, conflictMedicineName});
		} catch (Exception e) {
			logger.error(e);
			return 1;
		}
	}

	

	@Override
	public Integer addUserRole(final String userId, final String HospitalId, final Object[] roleId) {
		String sql = "insert into h_user_role(user_id,role_id,HospitalId) values(?,?,?)";
		int[] count = { 0 };
		try {
			count = super.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, userId);
					ps.setObject(2, roleId[i]);
					ps.setString(3, HospitalId);
				}

				@Override
				public int getBatchSize() {
					return roleId.length;
				}
			});
		} catch (DataAccessException e) {
			logger.error(e);
			throw e;
		}
		return Util.toInt(count);
	}

	

	
}
