package com.rfq.common.impl;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.rfq.entity.sys.SysRoler;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.rfq.common.BaseDaoInterface;
import com.rfq.entity.sys.Log;
import com.rfq.util.DaoUtil;
import com.rfq.utils.JsonUtils;
import com.rfq.utils.Util;

import net.sf.json.JSONObject;

/**
 * 
 */
@Repository("baseDao")
public class BaseDaoImpl implements BaseDaoInterface {

	// 日志
	private Logger logger = LogManager.getLogger(BaseDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;

	/**
	 * 根据实体查询
	 * 
	 * @param entity 实体
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> query(final T entity, boolean... isChild) {
		String tableName = DaoUtil.getTableName(entity);// 表名

		StringBuilder sql = new StringBuilder();// sql

		StringBuilder joinSqlQuery = new StringBuilder();// 链接要查询的字段

		StringBuilder joinSql = new StringBuilder();// 链接sql

		Map<String, Object> map = DaoUtil.jointSql(entity);

		List<Map<String, Object>> joinTableList = null == map.get("joinTableList") ? null
				: (List<Map<String, Object>>) map.get("joinTableList");

		if (null != joinTableList) {
			for (Map<String, Object> joinMap : joinTableList) {
				String joinTable = joinMap.get("table").toString();
				String id = joinMap.get("id").toString();
				String otherId = joinMap.get("otherId").toString();
				List<String> otherName = (List<String>) joinMap.get("otherName");
				String joinType = joinMap.get("joinType").toString();
				List<String> columnName = (List<String>) joinMap.get("columnName");
				for (int i = 0; i < otherName.size(); i++) {
					joinSqlQuery.append(",").append(joinTable).append("_a.").append(otherName.get(i).toString())
							.append(" as ").append(columnName.get(i).toString());
				}
				String jointable = joinType + " join " + joinTable + " as " + joinTable + "_a" + " on " + tableName
						+ "_a." + id + "=" + joinTable + "_a." + otherId + " ";
				joinSql.append(jointable);
			}
		}
		sql.append("select ").append(tableName).append("_a.*").append(joinSqlQuery).append(" from ").append(tableName)
				.append(" as ").append(tableName).append("_a ").append(joinSql).append(" where 1=1");

		sql.append(null == map.get("sql") ? "" : map.get("sql"));

		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");

		final boolean isChilds = (null != isChild && 0 != isChild.length && isChild[0]) ? true : false;// 是否查询关联表

		final List<Map<String, Object>> correlationTableList = isChilds
				? (null == map.get("correlationTableList") ? null
						: (List<Map<String, Object>>) map.get("correlationTableList"))
				: null;
		final List<T> entitys = new ArrayList<T>();
		try {

			logger.debug("\r\n" + sql.toString());

			jdbcTemplate.query(sql.toString(), obj, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					T t = DaoUtil.setEntity(entity, rs);
					if (isChilds && null != correlationTableList && correlationTableList.size() > 0) {
						try {
							setChlidTable(correlationTableList, t);
						} catch (Exception e) {
						}
					}
					entitys.add(t);
				}
			});
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of query===>" + sql);
			logger.warn(e);
		}
		return entitys;
	}

	/**
	 * 根据实体查询（可排序）
	 * 
	 * @param entity 实体
	 * @return
	 */
	public <T> List<T> queryBySort(final T entity, String strOrderby, String strDescOrAsc) {
		return queryV2(entity, strOrderby, strDescOrAsc, false);
	}

	/**
	 * 根据实体查询
	 * 
	 * @param entity 实体
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryV2(final T entity, String strOrderby, String strDescOrAsc, boolean... isChild) {
		String tableName = DaoUtil.getTableName(entity);// 表名

		StringBuilder sql = new StringBuilder();// sql

		StringBuilder joinSqlQuery = new StringBuilder();// 链接要查询的字段

		StringBuilder joinSql = new StringBuilder();// 链接sql

		Map<String, Object> map = DaoUtil.jointSql(entity);

		List<Map<String, Object>> joinTableList = null == map.get("joinTableList") ? null
				: (List<Map<String, Object>>) map.get("joinTableList");

		if (null != joinTableList) {
			for (Map<String, Object> joinMap : joinTableList) {
				String joinTable = joinMap.get("table").toString();
				String id = joinMap.get("id").toString();
				String otherId = joinMap.get("otherId").toString();
				List<String> otherName = (List<String>) joinMap.get("otherName");
				String joinType = joinMap.get("joinType").toString();
				List<String> columnName = (List<String>) joinMap.get("columnName");
				for (int i = 0; i < otherName.size(); i++) {
					joinSqlQuery.append(",").append(joinTable).append("_a.").append(otherName.get(i).toString())
							.append(" as ").append(columnName.get(i).toString());
				}
				String jointable = joinType + " join " + joinTable + " as " + joinTable + "_a" + " on " + tableName
						+ "_a." + id + "=" + joinTable + "_a." + otherId + " ";
				joinSql.append(jointable);
			}
		}
		sql.append("select ").append(tableName).append("_a.*").append(joinSqlQuery).append(" from ").append(tableName)
				.append(" as ").append(tableName).append("_a ").append(joinSql).append(" where 1=1");

		sql.append(null == map.get("sql") ? "" : map.get("sql"));

		sql.append(" order by " + tableName + "_a." + strOrderby + " " + strDescOrAsc + " ");

		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");

		final boolean isChilds = (null != isChild && 0 != isChild.length && isChild[0]) ? true : false;// 是否查询关联表

		final List<Map<String, Object>> correlationTableList = isChilds
				? (null == map.get("correlationTableList") ? null
						: (List<Map<String, Object>>) map.get("correlationTableList"))
				: null;
		final List<T> entitys = new ArrayList<T>();
		try {
			jdbcTemplate.query(sql.toString(), obj, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					T t = DaoUtil.setEntity(entity, rs);
					if (isChilds && null != correlationTableList && correlationTableList.size() > 0) {
						try {
							setChlidTable(correlationTableList, t);
						} catch (Exception e) {
						}
					}
					entitys.add(t);
				}
			});
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryV2===>" + sql);
			logger.warn(e);
		}
		return entitys;
	}

	public <T> void setChlidTable(List<Map<String, Object>> correlationTableList, T t) throws Exception {
		if (null != correlationTableList && correlationTableList.size() > 0) {// 如果有关联表
			for (Map<String, Object> entry : correlationTableList) {
				String fieldName = entry.get("fieldName").toString();// 字段名
				Class<?> className = (Class<?>) entry.get("className");// 关联实体
				String contentTable = entry.get("contentTable").toString();// 中间表
				String correlationTableField = entry.get("correlationTableField").toString();// 中间表关联字段
				String correlationTableOtherField = entry.get("correlationTableOtherField").toString();// 中间表被关联字段
				String correlationField = entry.get("correlationField").toString();// 关联字段
				String correlationOtherField = entry.get("correlationOtherField").toString();// 被关联字段
				if (null != contentTable && !"".equals(contentTable.trim())) {
					String contentsql = "select " + correlationTableOtherField + " from " + contentTable + " where "
							+ correlationTableField + " =?";
					Object obj = DaoUtil.getEntityDeclared(t, correlationField);
					List<Object> listObj = jdbcTemplate.query(contentsql, new Object[] { obj },
							new RowMapper<Object>() {
								@Override
								public Object mapRow(ResultSet rs, int i) throws SQLException {
									Object obj = rs.getObject(1);
									return obj;
								}
							});
					List<?> resultList = this.query(className, correlationOtherField, listObj);
					DaoUtil.setEntityDeclared(t, resultList, fieldName);
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(correlationOtherField, DaoUtil.getEntityDeclared(t, correlationField));
					List<?> resultList = this.queryOneToMony(className, map);
					logger.debug(t.getClass().getName() + " fill child object ======>" + fieldName);
					DaoUtil.setEntityDeclared(t, resultList, fieldName);
				}
			}
		}
	}

	/**
	 * 根据实体查询单个
	 * 
	 * @param entity 实体
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T queryByEntity(final T entity, boolean... isChild) {

		String tableName = DaoUtil.getTableName(entity);

		StringBuilder sql = new StringBuilder();

		StringBuilder joinSqlQuery = new StringBuilder();

		StringBuilder joinSql = new StringBuilder();

		Map<String, Object> map = DaoUtil.jointSql(entity);// 拼接字符串

		List<Map<String, Object>> joinTableList = null == map.get("joinTableList") ? null
				: (List<Map<String, Object>>) map.get("joinTableList");

		if (null != joinTableList) {
			for (Map<String, Object> joinMap : joinTableList) {
				String joinTable = joinMap.get("table").toString();
				String id = joinMap.get("id").toString();
				String otherId = joinMap.get("otherId").toString();
				List<String> otherName = (List<String>) joinMap.get("otherName");
				String joinType = joinMap.get("joinType").toString();
				List<String> columnName = (List<String>) joinMap.get("columnName");
				for (int i = 0; i < otherName.size(); i++) {
					joinSqlQuery.append(",").append(joinTable).append("_a.").append(otherName.get(i).toString())
							.append(" as ").append(columnName.get(i).toString());
				}
				String jointable = " " + joinType + " join " + joinTable + " as " + joinTable + "_a" + " on "
						+ tableName + "_a." + id + "=" + joinTable + "_a." + otherId;
				joinSql.append(jointable);
			}
		}
		sql.append("select ").append(tableName).append("_a.*").append(joinSqlQuery).append(" from ").append(tableName)
				.append(" as ").append(tableName).append("_a ").append(joinSql).append(" where 1=1");

		sql.append(null == map.get("sql") ? "" : map.get("sql"));
		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");
		final boolean isChilds = (null != isChild && 0 != isChild.length && isChild[0]) ? true : false;// 是否查询关联表

		final List<Map<String, Object>> correlationTableList = isChilds
				? (null == map.get("correlationTableList") ? null
						: (List<Map<String, Object>>) map.get("correlationTableList"))
				: null;
		final List<T> entitys = new ArrayList<T>();
		System.out.println("万恶的查询语句：" + sql.toString());
		try {
			jdbcTemplate.query(sql.toString(), obj, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					T t = DaoUtil.setEntity(entity, rs);
					if (isChilds && null != correlationTableList && correlationTableList.size() > 0) {
						try {
							setChlidTable(correlationTableList, t);
						} catch (Exception e) {
						}
					}
					entitys.add(t);
				}
			});
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryByEntity===>" + sql);
			logger.warn(e);
		}
		return null == entitys || 0 == entitys.size() || entitys.size() > 1 ? null : entitys.get(0);
	}

	/**
	 * 分页
	 * 
	 * @param entity 实体 T 参数对应 页数 行数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, Object> queryListByPage(final T entity, T... i) {

		String tableName = DaoUtil.getTableName(entity);

		StringBuilder sql = new StringBuilder();

		StringBuilder joinSqlQuery = new StringBuilder();

		StringBuilder joinSql = new StringBuilder();

		Map<String, Object> map = DaoUtil.jointSql(entity, i);// 拼接字符串

		List<Map<String, Object>> joinTableList = null == map.get("joinTableList") ? null
				: (List<Map<String, Object>>) map.get("joinTableList");

		if (null != joinTableList) {
			for (Map<String, Object> joinMap : joinTableList) {
				String joinTable = joinMap.get("table").toString();
				String id = joinMap.get("id").toString();
				String otherId = joinMap.get("otherId").toString();
				List<String> otherName = (List<String>) joinMap.get("otherName");
				String joinType = joinMap.get("joinType").toString();
				List<String> columnName = (List<String>) joinMap.get("columnName");
				for (int q = 0; q < otherName.size(); q++) {
					joinSqlQuery.append(",").append(joinTable).append("_a.").append(otherName.get(q).toString())
							.append(" as ").append(columnName.get(q).toString()).append(" ");
				}
				String jointable = joinType + " join " + joinTable + " as " + joinTable + "_a" + " on " + tableName
						+ "_a." + id + "=" + joinTable + "_a." + otherId + " ";
				joinSql.append(jointable);
			}
		}
		sql.append("select ").append(tableName).append("_a.*").append(joinSqlQuery).append(" from ").append(tableName)
				.append(" as ").append(tableName).append("_a ").append(joinSql).append(" where 1=1");

		sql.append(null == map.get("sql") ? "" : map.get("sql"));

		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");

		Integer total = queryListByPageNumSize(sql.toString(), obj);

		Integer row = DaoUtil.queryT("row", i);// 获得行数

		Integer page = DaoUtil.queryT("page", i);// 获得页数

		sql.append(" limit " + page + "," + row);
		Object isChild = null;
		try {
			isChild = i[i.length - 1];
		} catch (Exception e1) {
		}

		final boolean isChilds = (isChild instanceof Boolean && Boolean.parseBoolean(isChild.toString())) ? true
				: false;

		final List<Map<String, Object>> correlationTableList = isChilds
				? (null == map.get("correlationTableList") ? null
						: (List<Map<String, Object>>) map.get("correlationTableList"))
				: null;

		final List<T> entitys = new ArrayList<T>();
		try {
			logger.debug(sql);
			jdbcTemplate.query(sql.toString(), obj, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					T t = DaoUtil.setEntity(entity, rs);
					if (isChilds && null != correlationTableList && correlationTableList.size() > 0) {
						try {
							setChlidTable(correlationTableList, t);
						} catch (Exception e) {
						}
					}
					entitys.add(t);
				}
			});
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryListByPage===>" + sql);
			logger.warn(e);
		}
		map = new HashMap<String, Object>();
		map.put("data", entitys);
		map.put("total", total);
		return map;
	}

	public <T> Integer queryListByPageNumSize(String sql, Object obj[]) {
		return this.queryBysqlCount("select count(1) from (" + sql + ") f", obj);
	}

	@SuppressWarnings("unchecked")
	public <T> Integer queryListByPageNum(final T entity, T... i) {

		String tableName = DaoUtil.getTableName(entity);

		StringBuilder sql = new StringBuilder();

		StringBuilder joinSqlQuery = new StringBuilder();

		StringBuilder joinSql = new StringBuilder();

		Map<String, Object> map = DaoUtil.jointSql(entity, i);// 拼接字符串

		List<Map<String, Object>> joinTableList = null == map.get("joinTableList") ? null
				: (List<Map<String, Object>>) map.get("joinTableList");

		if (null != joinTableList) {
			for (Map<String, Object> joinMap : joinTableList) {
				String joinTable = joinMap.get("table").toString();
				String id = joinMap.get("id").toString();
				String otherId = joinMap.get("otherId").toString();
				List<String> otherName = (List<String>) joinMap.get("otherName");
				String joinType = joinMap.get("joinType").toString();
				List<String> columnName = (List<String>) joinMap.get("columnName");
				for (int q = 0; q < otherName.size(); q++) {
					joinSqlQuery.append(",").append(joinTable).append("_a.").append(otherName.get(q).toString())
							.append(" as ").append(columnName.get(q).toString());
				}
				String jointable = joinType + " join " + joinTable + " as " + joinTable + "_a" + " on " + tableName
						+ "_a." + id + "=" + joinTable + "_a." + otherId;
				joinSql.append(jointable);
			}
		}
		sql.append("select count(1) ").append(" from ").append(tableName).append(" as ").append(tableName).append("_a ")
				.append(joinSql).append(" where 1=1");

		sql.append(null == map.get("sql") ? "" : map.get("sql"));

		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");

		Integer total = 0;
		try {
			total = jdbcTemplate.queryForObject(sql.toString(), obj, Integer.class);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryListByPageNum===>" + sql);
			logger.warn(e);
		}
		return total;
	}

	/**
	 * 修改必须有ID 根据ID
	 */
	public <T> Integer updateEntity(T entity) {
		String sql = "update " + DaoUtil.getTableName(entity);
		Map<String, Object> map = DaoUtil.jointSetSql(entity);
		if (null == map.get("sql")) {
			return 0;
		}
		sql += null == map.get("sql") ? "" : map.get("sql").toString();
		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");
		Integer i = 0;
		try {
			System.out.println("\n\n" + sql);
			i = jdbcTemplate.update(sql, obj);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of updateEntity===>" + sql);
			logger.error(e);
			throw e;
		}
		return i;
	}

	/**
	 * 批量修改
	 */
	@Override
	public <T> Integer batchUpdate(final List<T> entitys) {
		String sql = "update " + DaoUtil.getTableName(entitys.get(0));
		Map<String, Object> map = DaoUtil.jointSetSql(entitys.get(0));
		if (null == map.get("sql")) {
			return 0;
		}
		sql += null == map.get("sql") ? "" : map.get("sql").toString();
		// Object[] obj =null==map.get("obj")?new Object[0]:(Object[])
		// map.get("obj");
		int[] i = { 0 };
		try {
			i = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int q) throws SQLException {
					Map<String, Object> map = DaoUtil.jointSetSql(entitys.get(q));
					Object[] obj = (Object[]) map.get("obj");
					for (int j = 1; j <= obj.length; j++) {
						ps.setObject(j, obj[j - 1]);
					}
				}

				@Override
				public int getBatchSize() {
					return entitys.size();
				}
			});
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of batchUpdate===>" + sql);
			logger.error(e);
			throw e;
		}
		return Util.toInt(i);
	}

	/**
	 * 删除 根据属性
	 */
	@SuppressWarnings("unchecked")
	public <T> Integer deleteEntity(T entity) {
		String sql = "delete from " + DaoUtil.getTableName(entity) + " where 1=1";
		Map<String, Object> map = DaoUtil.jointSql(entity);// 拼接字符串
		sql += null == map.get("sql") ? "" : map.get("sql");
		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");
		int i = 0;
		try {
			i = jdbcTemplate.update(sql, obj);
			Log log = new Log();
			log.setModular(DaoUtil.getTableName(entity));
			log.setContent("删除数据");
			log.setType("删除");
			log.setCreateTime(System.currentTimeMillis() / 1000);
			log.setDetails(JSONObject.fromObject(entity).toString());
			i = jdbcTemplate.update(sql, obj);
			Integer state = i > 0 ? 1 : 0;
			log.setState(state);
			/*
			 * try { CustomerUserDetails userDetail =
			 * (CustomerUserDetails)SecurityContextHolder
			 * .getContext().getAuthentication().getPrincipal();
			 * log.setUserId(userDetail.getUser().getId());
			 * log.setHospitalId(userDetail.getUser().getHospitalId()); } catch (Exception
			 * e) { log.setContent("未获取到UserId"); }
			 */
			try {
				SysRoler user = (SysRoler) SecurityUtils.getSubject().getPrincipal();
				log.setCreateUserId(user.getId());
			} catch (Exception e) {
				log.setContent("未获取到User");
				logger.error(e);
			}
			addEntity(log);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of deleteEntity===>" + sql);
			logger.error(e);
			throw e;
		}
		return i;
	}

	/**
	 * 增加
	 */
	public <T> Object addEntity(T entity) {
		String sql = "insert into " + DaoUtil.getTableName(entity);
		Map<String, Object> map = DaoUtil.jointInsertSql(entity);// 拼接字符串
		sql += null == map.get("sql") ? "" : map.get("sql");
		final Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			final String s = sql;
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(s, PreparedStatement.RETURN_GENERATED_KEYS);
					int i = 1;
					for (Object o : obj) {
						ps.setObject(i++, o);
					}
					return ps;
				}
			}, keyHolder);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of addEntity===>" + sql);
			logger.error(e);
			throw e;
		}
		return null == keyHolder.getKey() ? 1 : keyHolder.getKey();
	}

	/**
	 * 增加
	 */
	public <T> Object addEntityUUID(T entity) {
		String uuid = Util.getUUID();
		String id = DaoUtil.getAnnotationId(entity.getClass());
		try {
			DaoUtil.setEntityDeclared(entity, uuid, id);
		} catch (Exception e1) {
		}
		String sql = "insert into " + DaoUtil.getTableName(entity);
		Map<String, Object> map = DaoUtil.jointInsertSql(entity);// 拼接字符串
		sql += null == map.get("sql") ? "" : map.get("sql");
		final Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");
		try {
			jdbcTemplate.update(sql, obj);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of addEntityUUID===>" + sql);
			logger.error(e);
			throw e;
		}
		return uuid;
	}

	/**
	 * 增加
	 */
	public <T> Object addEntity(List<T> entity) {
		String sql = "insert into " + DaoUtil.getTableName(entity.get(0));
		int i = 0;
		for (T t : entity) {
			Map<String, Object> map = DaoUtil.jointInsertSql(t);// 拼接字符串
			sql += null == map.get("sql") ? "" : map.get("sql");
			Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");
			try {
				i = jdbcTemplate.update(sql, obj);
			} catch (DataAccessException e) {
				logger.warn("DataAccessException of addEntity===>" + sql);
				logger.error(e);
				throw e;
			}
		}
		return i;
	}

	/**
	 * 修改或增加
	 */
	public <T> Integer updateOrAdd(T entity) {
		String sql = "replace into " + DaoUtil.getTableName(entity);
		Map<String, Object> map = DaoUtil.jointInsertSql(entity);// 拼接字符串
		sql += null == map.get("sql") ? "" : map.get("sql").toString();
		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");
		int i = 0;
		try {
			i = jdbcTemplate.update(sql, obj);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of updateOrAdd===>" + sql);
			logger.error(e);
			throw e;
		}
		return i;
	}

	/**
	 * 根据类和参数查询
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> query(final Class<T> t, Map<String, Object> map, boolean... isChild) {

		String tableName = DaoUtil.getTableName(t);// 表名

		StringBuilder sql = new StringBuilder();// sql

		StringBuilder joinSqlQuery = new StringBuilder();// 链接要查询的字段

		StringBuilder joinSql = new StringBuilder();// 链接sql

		Map<String, Object> query = DaoUtil.jointSql(t, map);

		List<Map<String, Object>> joinTableList = DaoUtil.queryJoin(t);

		if (null != joinTableList) {
			for (Map<String, Object> joinMap : joinTableList) {
				String joinTable = joinMap.get("table").toString();
				String id = joinMap.get("id").toString();
				String otherId = joinMap.get("otherId").toString();
				List<String> otherName = (List<String>) joinMap.get("otherName");
				String joinType = joinMap.get("joinType").toString();
				List<String> columnName = (List<String>) joinMap.get("columnName");
				for (int i = 0; i < otherName.size(); i++) {
					joinSqlQuery.append(",").append(joinTable).append("_a.").append(otherName.get(i).toString())
							.append(" as ").append(columnName.get(i).toString());
				}
				String jointable = joinType + " join " + joinTable + " as " + joinTable + "_a" + " on " + tableName
						+ "_a." + id + "=" + joinTable + "_a." + otherId;
				joinSql.append(jointable);
			}
		}
		sql.append("select ").append(tableName).append("_a.*").append(joinSqlQuery).append(" from ").append(tableName)
				.append(" as ").append(tableName).append("_a ").append(joinSql).append(" where 1=1");

		sql.append(null == query ? "" : query.get("sql"));
		;

		Object[] obj = null == query ? new Object[0] : (Object[]) query.get("obj");
		final boolean isChilds = (null != isChild && 0 != isChild.length && isChild[0]) ? true : false;// 是否查询关联表

		final List<Map<String, Object>> oneToMony = isChilds ? DaoUtil.queryOneToMony(t) : null;
		List<T> entitys = new ArrayList<T>();
		try {
			/*
			 * jdbcTemplate.query(sql.toString(), obj,new RowCallbackHandler() {
			 * 
			 * @Override public void processRow(ResultSet rs) throws SQLException { T t1 =
			 * DaoUtil.setEntity(t, rs); if (isChilds && null != oneToMony &&
			 * oneToMony.size() > 0) { try { setChlidTable(oneToMony, t1); } catch
			 * (Exception e) { } } entitys.add(t1); } });
			 */
			entitys = jdbcTemplate.query(sql.toString(), obj, new RowMapper<T>() {
				@Override
				public T mapRow(ResultSet rs, int i) throws SQLException {
					T t1 = DaoUtil.setEntity(t, rs);
					if (isChilds && null != oneToMony && oneToMony.size() > 0) {
						try {
							setChlidTable(oneToMony, t1);
						} catch (Exception e) {
						}
					}
					return t1;
				}
			});
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of query ...===>" + sql);
			logger.warn(e);
		}
		return entitys;
	}

	/**
	 * 根据类和参数查询 递归查询 无接口实现 不允许集成
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryOneToMony(final Class<T> t, Map<String, Object> map) {

		String tableName = DaoUtil.getTableName(t);// 表名

		StringBuilder sql = new StringBuilder();// sql

		StringBuilder joinSqlQuery = new StringBuilder();// 链接要查询的字段

		StringBuilder joinSql = new StringBuilder();// 链接sql

		Map<String, Object> query = DaoUtil.jointSql(t, map);

		List<Map<String, Object>> joinTableList = DaoUtil.queryJoin(t);

		if (null != joinTableList) {
			for (Map<String, Object> joinMap : joinTableList) {
				String joinTable = joinMap.get("table").toString();
				String id = joinMap.get("id").toString();
				String otherId = joinMap.get("otherId").toString();
				List<String> otherName = (List<String>) joinMap.get("otherName");
				String joinType = joinMap.get("joinType").toString();
				List<String> columnName = (List<String>) joinMap.get("columnName");
				for (int i = 0; i < otherName.size(); i++) {
					joinSqlQuery.append(",").append(joinTable).append("_a.").append(otherName.get(i).toString())
							.append(" as ").append(columnName.get(i).toString());
				}
				String jointable = joinType + " join " + joinTable + " as " + joinTable + "_a" + " on " + tableName
						+ "_a." + id + "=" + joinTable + "_a." + otherId + " ";
				joinSql.append(jointable);
			}
		}
		sql.append("select ").append(tableName).append("_a.*").append(joinSqlQuery).append(" from ").append(tableName)
				.append(" as ").append(tableName).append("_a ").append(joinSql).append(" where 1=1");

		sql.append(null == query.get("sql") ? "" : query.get("sql"));
		;

		Object[] obj = null == query.get("obj") ? new Object[0] : (Object[]) query.get("obj");
		final List<Map<String, Object>> oneToMony = DaoUtil.queryOneToMony(t);

		List<T> entitys = new ArrayList<T>();
		try {
			/*
			 * jdbcTemplate.query(sql.toString(), obj,new RowCallbackHandler() {
			 * 
			 * @Override public void processRow(ResultSet rs) throws SQLException { T t1 =
			 * DaoUtil.setEntity(t, rs); if (null != oneToMony && oneToMony.size() > 0) {
			 * try { setChlidTable(oneToMony, t1); } catch (Exception e) { } }
			 * entitys.add(t1); } });
			 */
			entitys = jdbcTemplate.query(sql.toString(), obj, new RowMapper<T>() {
				@Override
				public T mapRow(ResultSet rs, int i) throws SQLException {
					T t1 = DaoUtil.setEntity(t, rs);
					if (null != oneToMony && oneToMony.size() > 0) {
						try {
							setChlidTable(oneToMony, t1);
						} catch (Exception e) {
						}
					}
					return t1;
				}
			});
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryOneToMony===>" + sql);
			logger.warn(e);
		}
		return entitys;
	}

	/**
	 * 根据类和批量查询 递归查询 无接口实现 不允许继承
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> query(final Class<T> t, String name, List<Object> lists) {

		String tableName = DaoUtil.getTableName(t);// 表名

		StringBuilder sql = new StringBuilder();// sql

		StringBuilder joinSqlQuery = new StringBuilder();// 链接要查询的字段

		StringBuilder joinSql = new StringBuilder();// 链接sql

		List<Map<String, Object>> joinTableList = DaoUtil.queryJoin(t);

		if (null != joinTableList) {
			for (Map<String, Object> joinMap : joinTableList) {
				String joinTable = joinMap.get("table").toString();
				String id = joinMap.get("id").toString();
				String otherId = joinMap.get("otherId").toString();
				List<String> otherName = (List<String>) joinMap.get("otherName");
				String joinType = joinMap.get("joinType").toString();
				List<String> columnName = (List<String>) joinMap.get("columnName");
				for (int i = 0; i < otherName.size(); i++) {
					joinSqlQuery.append(",").append(joinTable).append("_a.").append(otherName.get(i).toString())
							.append(" as ").append(columnName.get(i).toString());
				}
				String jointable = joinType + " join " + joinTable + " as " + joinTable + "_a" + " on " + tableName
						+ "_a." + id + "=" + joinTable + "_a." + otherId;
				joinSql.append(jointable);
			}
		}
		sql.append("select ").append(tableName).append("_a.*").append(joinSqlQuery).append(" from ").append(tableName)
				.append(" as ").append(tableName).append("_a ").append(joinSql).append(" where 1=1 and ").append(name)
				.append("=?");

		final List<T> entitys = new ArrayList<T>();

		final List<Map<String, Object>> oneToMony = DaoUtil.queryOneToMony(t);
		try {
			for (Object list : lists) {
				jdbcTemplate.query(sql.toString(), new Object[] { list }, new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						T t1 = DaoUtil.setEntity(t, rs);
						if (null != oneToMony && oneToMony.size() > 0) {
							try {
								setChlidTable(oneToMony, t1);
							} catch (Exception e) {
							}
						}
						entitys.add(t1);
					}
				});
			}
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of query .===>" + sql);
			logger.warn(e);
		}
		return entitys;
	}

	/**
	 * 根据类和参数查询单个
	 */
	@SuppressWarnings("unchecked")
	public <T> T queryOne(final Class<T> t, Map<String, Object> map, boolean... isChild) {

		String tableName = DaoUtil.getTableName(t);// 表名

		StringBuilder sql = new StringBuilder();// sql

		StringBuilder joinSqlQuery = new StringBuilder();// 链接要查询的字段

		StringBuilder joinSql = new StringBuilder();// 链接sql

		Map<String, Object> querys = DaoUtil.jointSql(t, map);

		List<Map<String, Object>> joinTableList = DaoUtil.queryJoin(t);
		logger.debug("你的sql");
		if (null != joinTableList) {
			for (Map<String, Object> joinMap : joinTableList) {
				String joinTable = joinMap.get("table").toString();
				String id = joinMap.get("id").toString();
				String otherId = joinMap.get("otherId").toString();
				List<String> otherName = (List<String>) joinMap.get("otherName");
				String joinType = joinMap.get("joinType").toString();
				List<String> columnName = (List<String>) joinMap.get("columnName");
				for (int i = 0; i < otherName.size(); i++) {
					joinSqlQuery.append(",").append(joinTable).append("_a.").append(otherName.get(i).toString())
							.append(" as ").append(columnName.get(i).toString());
				}
				String jointable = joinType + " join " + joinTable + " as " + joinTable + "_a" + " on " + tableName
						+ "_a." + id + "=" + joinTable + "_a." + otherId;
				joinSql.append(jointable);
			}
		}
		sql.append("select ").append(tableName).append("_a.*").append(joinSqlQuery).append(" from ").append(tableName)
				.append(" as ").append(tableName).append("_a ").append(joinSql).append(" where 1=1");

		sql.append(null == querys ? "" : querys.get("sql"));
		logger.debug(sql.toString() + "你的sql");
		Object[] obj = null == querys ? new Object[0] : (Object[]) querys.get("obj");
		final boolean isChilds = (null != isChild && 0 != isChild.length && isChild[0]) ? true : false;// 是否查询关联表

		final List<Map<String, Object>> oneToMony = isChilds ? DaoUtil.queryOneToMony(t) : null;

		final List<T> entitys = new ArrayList<T>();
		logger.debug(sql.toString() + "你的sql");
		try {
			jdbcTemplate.query(sql.toString(), obj, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					T t1 = DaoUtil.setEntity(t, rs);
					if (isChilds && null != oneToMony && oneToMony.size() > 0) {
						try {
							setChlidTable(oneToMony, t1);
						} catch (Exception e) {
						}
					}
					entitys.add(t1);
				}
			});
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryOne .===>" + sql);
			logger.warn(e);
		}
		return null == entitys || 0 == entitys.size() || entitys.size() > 1 ? null : entitys.get(0);
	}

	/**
	 * 根据类和id查询
	 */
	@SuppressWarnings("unchecked")
	public <T> T queryById(final Class<T> t, Object id, boolean... isChild) {

		String tableName = DaoUtil.getTableName(t);// 表名

		StringBuilder sql = new StringBuilder();// sql

		StringBuilder joinSqlQuery = new StringBuilder();// 链接要查询的字段

		StringBuilder joinSql = new StringBuilder();// 链接sql

		Map<String, Object> map = DaoUtil.jointIdSql(t, id);

		List<Map<String, Object>> joinTableList = DaoUtil.queryJoin(t);

		if (null != joinTableList) {
			for (Map<String, Object> joinMap : joinTableList) {
				String joinTable = joinMap.get("table").toString();
				String ids = joinMap.get("id").toString();
				String otherId = joinMap.get("otherId").toString();
				List<String> otherName = (List<String>) joinMap.get("otherName");
				String joinType = joinMap.get("joinType").toString();
				List<String> columnName = (List<String>) joinMap.get("columnName");
				for (int i = 0; i < otherName.size(); i++) {
					joinSqlQuery.append(",").append(joinTable).append("_a.").append(otherName.get(i).toString())
							.append(" as ").append(columnName.get(i).toString());
				}
				String jointable = joinType + " join " + joinTable + " as " + joinTable + "_a" + " on " + tableName
						+ "_a." + ids + "=" + joinTable + "_a." + otherId + " ";
				joinSql.append(jointable);
			}
		}
		sql.append("select ").append(tableName).append("_a.*").append(joinSqlQuery).append(" from ").append(tableName)
				.append(" as ").append(tableName).append("_a ").append(joinSql).append(" where 1=1");

		sql.append(null == map.get("sql") ? "" : map.get("sql"));

		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");
		final boolean isChilds = (null != isChild && 0 != isChild.length && isChild[0]) ? true : false;// 是否查询关联表

		final List<Map<String, Object>> oneToMony = isChilds ? DaoUtil.queryOneToMony(t) : null;

		final List<T> entitys = new ArrayList<T>();
		try {
			System.out.println("\n\nsql:" + sql.toString());
			jdbcTemplate.query(sql.toString(), obj, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					T t1 = DaoUtil.setEntity(t, rs);
					if (isChilds && null != oneToMony && oneToMony.size() > 0) {
						try {
							setChlidTable(oneToMony, t1);
						} catch (Exception e) {
						}
					}
					entitys.add(t1);
				}
			});
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryById .===>" + sql);
			logger.warn(e);
		}
		return null == entitys || 0 == entitys.size() || entitys.size() > 1 ? null : entitys.get(0);
	}

	/**
	 * 根据类和参数查询分页
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, Object> queryListByPage(final Class<T> entity, Map<String, Object> param,
			boolean... isChild) {

		String tableName = DaoUtil.getTableName(entity);// 表名

		StringBuilder sql = new StringBuilder();// sql

		StringBuilder joinSqlQuery = new StringBuilder();// 链接要查询的字段

		StringBuilder joinSql = new StringBuilder();// 链接sql

		Map<String, Object> map = DaoUtil.jointSql(entity, param);// 拼接字符串

		List<Map<String, Object>> joinTableList = DaoUtil.queryJoin(entity);

		if (null != joinTableList) {
			for (Map<String, Object> joinMap : joinTableList) {
				String joinTable = joinMap.get("table").toString();
				String id = joinMap.get("id").toString();
				String otherId = joinMap.get("otherId").toString();
				List<String> otherName = (List<String>) joinMap.get("otherName");
				String joinType = joinMap.get("joinType").toString();
				List<String> columnName = (List<String>) joinMap.get("columnName");
				for (int i = 0; i < otherName.size(); i++) {
					joinSqlQuery.append(",").append(joinTable).append("_a.").append(otherName.get(i).toString())
							.append(" as ").append(columnName.get(i).toString());
				}
				String jointable = joinType + " join " + joinTable + " as " + joinTable + "_a" + " on " + tableName
						+ "_a." + id + "=" + joinTable + "_a." + otherId;
				joinSql.append(jointable);
			}
		}
		sql.append("select ").append(tableName).append("_a.*").append(joinSqlQuery).append(" from ").append(tableName)
				.append(" as ").append(tableName).append("_a ").append(joinSql).append(" where 1=1");

		sql.append(null == map.get("sql") ? "" : map.get("sql"));

		Integer row = DaoUtil.queryT("row", param.get("page"), param.get("row"));// 获得行数

		Integer page = DaoUtil.queryT("page", param.get("page"));// 获得页数

		sql.append(" limit " + page + "," + row);

		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");

		final boolean isChilds = (null != isChild && 0 != isChild.length && isChild[0]) ? true : false;// 是否查询关联表

		final List<Map<String, Object>> oneToMony = isChilds ? DaoUtil.queryOneToMony(entity) : null;

		final List<T> entitys = new ArrayList<T>();

		try {
			jdbcTemplate.query(sql.toString(), obj, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					T t = DaoUtil.setEntity(entity, rs);
					if (isChilds && null != oneToMony && oneToMony.size() > 0) {
						try {
							setChlidTable(oneToMony, t);
						} catch (Exception e) {
						}
					}
					entitys.add(t);
				}
			});
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryListByPage===>" + sql);
			logger.warn(e);
		}
		map = new HashMap<String, Object>();
		Integer total = queryListByPageNum(entity, param);
		map.put("data", entitys);
		map.put("total", total);
		return map;
	}

	@SuppressWarnings("unchecked")
	public <T> Integer queryListByPageNum(final Class<T> entity, Map<String, Object> param) {

		String tableName = DaoUtil.getTableName(entity);// 表名

		StringBuilder sql = new StringBuilder();// sql

		StringBuilder joinSqlQuery = new StringBuilder();// 链接要查询的字段

		StringBuilder joinSql = new StringBuilder();// 链接sql

		Map<String, Object> map = DaoUtil.jointSql(entity, param);// 拼接字符串

		List<Map<String, Object>> joinTableList = DaoUtil.queryJoin(entity);

		if (null != joinTableList) {
			for (Map<String, Object> joinMap : joinTableList) {
				String joinTable = joinMap.get("table").toString();
				String id = joinMap.get("id").toString();
				String otherId = joinMap.get("otherId").toString();
				List<String> otherName = (List<String>) joinMap.get("otherName");
				String joinType = joinMap.get("joinType").toString();
				List<String> columnName = (List<String>) joinMap.get("columnName");
				for (int i = 0; i < otherName.size(); i++) {
					joinSqlQuery.append(",").append(joinTable).append("_a.").append(otherName.get(i).toString())
							.append(" as ").append(columnName.get(i).toString());
				}
				String jointable = joinType + " join " + joinTable + " as " + joinTable + "_a" + " on " + tableName
						+ "_a." + id + "=" + joinTable + "_a." + otherId;
				joinSql.append(jointable);
			}
		}
		sql.append("select count(1) ").append(" from ").append(tableName).append(" as ").append(tableName).append("_a ")
				.append(joinSql).append(" where 1=1");

		sql.append(null == map.get("sql") ? "" : map.get("sql"));

		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");

		Integer total = 0;

		try {
			total = jdbcTemplate.queryForObject(sql.toString(), obj, Integer.class);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryListByPageNum===>" + sql);
			logger.warn(e);
		}
		return total;
	}

	@Override
	public <T> Integer deleteEntity(Class<T> entity, Object id) {
		String tableName = DaoUtil.getTableName(entity);
		String sql = "delete from " + tableName + " where 1=1";
		Map<String, Object> map = DaoUtil.jointIdSql(entity, id, true);
		sql += null == map.get("sql") ? "" : map.get("sql");
		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");
		Integer i = 0;
		try {
			Log log = new Log();
			log.setModular(DaoUtil.getTableName(entity));
			log.setContent("删除数据");
			log.setType("删除");
			log.setCreateTime(System.currentTimeMillis() / 1000);
			Object object = queryById(entity, id, false);
			log.setDetails(JSONObject.fromObject(object).toString());
			i = jdbcTemplate.update(sql, obj);
			Integer state = i > 0 ? 1 : 0;
			log.setState(state);
			/*
			 * try { CustomerUserDetails userDetail =
			 * (CustomerUserDetails)SecurityContextHolder
			 * .getContext().getAuthentication().getPrincipal();
			 * log.setUserId(userDetail.getUser().getId());
			 * log.setHospitalId(userDetail.getUser().getHospitalId()); } catch (Exception
			 * e) { log.setContent("未获取到UserId"); }
			 */
			try {
				SysRoler user = (SysRoler) SecurityUtils.getSubject().getPrincipal();
				if (user != null) {
					log.setCreateUserId(user.getId());
				} else {
					log.setContent("未获取到User");
				}
			} catch (Exception e) {
				log.setContent("未获取到User");
				logger.error(e);
			}
			addEntity(log);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of deleteEntity===>" + sql);
			logger.warn(e);
			throw e;
		}
		return i;
	}

	/**
	 * 批量删除
	 */
	@Override
	public <T> Integer deleteEntityList( Class<T> entity, final List<Object> ids) {
		String sql = "delete from " + DaoUtil.getTableName(entity) + " where 1=1";
		String sb = DaoUtil.jointIdsSql(entity);
		sql += null == sb || "".equals(sb) ? "" : sb.toString();
		int[] i = { 0 };
		try {
			StringBuffer stringBuffer = new StringBuffer();
			for (Object id : ids) {
				stringBuffer.append(JSONObject.fromObject(queryById(entity, id, false)).toString());
			}
			i = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setObject(1, ids.get(i));
				}

				@Override
				public int getBatchSize() {
					return ids.size();
				}
			});
			SysRoler user = GetLoginUser();
			Log log = new Log();
			log.setModular("删除");
			log.setContent(DaoUtil.getTableName(entity));
			log.setCreateUserId(user.getId());
			log.setIp("");//TODO: Util.getIp(request)
			log.setType("删除");
			log.setCreateTime(System.currentTimeMillis() / 1000);
			log.setDetails(stringBuffer.toString());
			log.setState(1);
			addEntity(log);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of deleteEntityList===>" + sql);
			logger.warn(e);
			throw e;
		}
		return Util.toInt(i);
	}

	@Override
	public <T> Integer exist(Class<T> entity, Map<String, Object> param) {
		String sql = "select count(1) from " + DaoUtil.getTableName(entity) + " where 1=1";

		Map<String, Object> map = DaoUtil.jointSql(entity, param);// 拼接字符串

		sql += null == map.get("sql") ? "" : map.get("sql");

		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");

		Integer i = 0;
		try {
			i = jdbcTemplate.queryForObject(sql, obj, Integer.class);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of exist===>" + sql);
			logger.warn(e);
		}
		return i;
	}

	@Override
	public <T> Integer existId(Class<T> entity, Map<String, Object> param) {
		String sql = "select count(1) from " + DaoUtil.getTableName(entity) + " where 1=1";

		Map<String, Object> map = DaoUtil.jointSqlLastNotEquals(entity, param);// 拼接字符串

		sql += null == map.get("sql") ? "" : map.get("sql");

		Object[] obj = null == map.get("obj") ? new Object[0] : (Object[]) map.get("obj");

		Integer i = 0;
		try {
			i = jdbcTemplate.queryForObject(sql, Integer.class, obj);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of existId===>" + sql);
			logger.warn(e);
		}
		return i;
	}

	@Override
	public Integer delete(String sql, Object[] obj) {
		int i = 0;
		try {
			String selectSql = sql.substring(sql.toLowerCase().indexOf("from"), sql.length());
			selectSql = "select * " + selectSql;
			Map<String, Object> map = queryBysqlMap(selectSql, obj);
			i = jdbcTemplate.update(sql, obj);
			Log log = new Log();
			log.setDetails(JSONObject.fromObject(map).toString());
			log.setContent("删除数据");
			log.setType("删除");
			log.setCreateTime(System.currentTimeMillis() / 1000);
			log.setState(0);
			/*
			 * try { CustomerUserDetails userDetail =
			 * (CustomerUserDetails)SecurityContextHolder
			 * .getContext().getAuthentication().getPrincipal();
			 * log.setUserId(userDetail.getUser().getId());
			 * log.setHospitalId(userDetail.getUser().getHospitalId()); } catch (Exception
			 * e) { log.setContent("未获取到UserId"); }
			 */
			try {
				SysRoler user = (SysRoler) SecurityUtils.getSubject().getPrincipal();
				if (user != null) {
					log.setCreateUserId(user.getId());
				} else {
					log.setContent("未获取到User");
				}
			} catch (Exception e) {
				log.setContent("未获取到User");
				logger.error(e);
			}
			addEntity(log);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of delete===>" + sql);
			logger.warn(e);
			throw e;
		}
		return i;
	}

	@Override
	public Integer deleteBatch(String sql, final List<Object> list) {
		int[] i = { 0 };
		try {
			String selectSql = sql.substring(sql.indexOf("from"), sql.length());
			selectSql = "select * " + selectSql;
			StringBuffer stringBuffer = new StringBuffer();
			Map<String, Object> map = null;
			for (Object obj : list) {
				map = queryBysqlMap(selectSql, new Object[] { obj });
				stringBuffer.append(JSONObject.fromObject(map).toString());
			}
			i = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setObject(1, list.get(i));
				}

				@Override
				public int getBatchSize() {
					return list.size();
				}
			});
			Log log = new Log();
			log.setContent("删除数据");
			log.setType("删除");
			log.setCreateTime(System.currentTimeMillis() / 1000);
			log.setState(0);
			log.setDetails(stringBuffer.toString());
			/*
			 * try { CustomerUserDetails userDetail =
			 * (CustomerUserDetails)SecurityContextHolder
			 * .getContext().getAuthentication().getPrincipal();
			 * log.setUserId(userDetail.getUser().getId());
			 * log.setHospitalId(userDetail.getUser().getHospitalId()); } catch (Exception
			 * e) { log.setContent("未获取到UserId"); }
			 */
			try {
				SysRoler user = (SysRoler) SecurityUtils.getSubject().getPrincipal();
				log.setCreateUserId(user.getId());
			} catch (Exception e) {
				log.setContent("未获取到User");
				logger.error(e);
			}
			addEntity(log);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of deleteBatch===>" + sql);
			logger.warn(e);
			throw e;
		}
		return Util.toInt(i);
	}

	@Override
	public Integer queryBysqlCount(String sql, Object[] obj) {
		Integer i = 0;
		try {
			i = jdbcTemplate.queryForObject(sql, obj, Integer.class);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryBysqlCount===>" + sql);
			logger.warn(e);
		}
		return i;
	}

	@Override
	public Double queryBysqlCountDouble(String sql, Object[] obj) {
		Double i = 0.0;
		try {
			i = jdbcTemplate.queryForObject(sql, obj, Double.class);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryBysqlCount===>" + sql);
			logger.warn(e);
		}
		return i;
	}

	@Override
	public String queryStringValue(String sql, Object[] obj) {
		String result = "";
		try {
			result = jdbcTemplate.queryForObject(sql, obj, String.class);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryStringValue===>" + sql);
			logger.warn(e);
		}
		return result;
	}

	/*
	 * @Override public String queryStringValue(String sql, Object[] obj,Integer
	 * groupConcatMaxLen_K ) { String result = ""; if(groupConcatMaxLen_K==null)
	 * groupConcatMaxLen_K=1; try { String
	 * sqlset="set session group_concat_max_len = "+(1024*groupConcatMaxLen_K);
	 * jdbcTemplate.execute(sqlset); result = jdbcTemplate.queryForObject(sql, obj,
	 * String.class); } catch (DataAccessException e) { logger.warn(e); } return
	 * result; }
	 */

	@Override
	public List<Map<String, Object>> queryBysqlList(String sql, Object[] obj) {
		List<Map<String, Object>> list = null;
		try {
			list = jdbcTemplate.queryForList(sql, obj);
		} catch (DataAccessException e) {
			logger.warn("DataAccessException of queryBysqlList===>" + sql);
			logger.warn(e);
		}
		return list;
	}

	@Override
	public <T> List<T> queryBysqlListBean(final Class<T> t, String sql, Object[] obj) {
		List<T> entity = jdbcTemplate.query(sql, obj, new RowMapper<T>() {
			@Override
			public T mapRow(ResultSet rs, int i) throws SQLException {
				return DaoUtil.setEntity(t, rs);
			}
		});
		return entity;
	}

	@Override
	public Map<String, Object> queryBysqlMap(String sql, Object[] obj) {
		Map<String, Object> map = null;
		try {
			map = jdbcTemplate.queryForMap(sql, obj);
		} catch (DataAccessException e) {
			String data = obj == null ? "" : ArrayUtils.toString(obj, ",");
			logger.warn("未获取到期望数据(DataAccessException) of queryBysqlMap===>" + sql + " 参数:" + data);
			// logger.warn(e);
		}
		return map;
	}

	@Override
	public Integer update(String sql, Object[] obj) {
		Integer i = 0;
		try {
			i = jdbcTemplate.update(sql, obj);
		} catch (DataAccessException e) {
			String data = obj == null ? "" : ArrayUtils.toString(obj, ",");
			logger.warn("未获 更新 到期望数据(DataAccessException) of update===>" + sql + " 参数:" + data);
			// logger.warn(e);
			throw e;
		}
		return i;
	}

	@Override
	public void execute(String sql) {
		try {
			jdbcTemplate.execute(sql);
		} catch (DataAccessException e) {
			logger.warn("未获 执行 到期望数据(DataAccessException) of execute===>" + sql);
			logger.error(e);
			throw e;
		}
	}

	@Override
	public synchronized String orderNo() {
		String orderNo = null;
		try {
			orderNo = jdbcTemplate.execute(new CallableStatementCreator() {
				public CallableStatement createCallableStatement(Connection con) throws SQLException {
					CallableStatement cs = con.prepareCall("{call h_orderNo(?)}");
					cs.registerOutParameter(1, SqlTypeValue.TYPE_UNKNOWN);// 注册输出参数的类型
					return cs;
				}
			}, new CallableStatementCallback<String>() {

				public String doInCallableStatement(CallableStatement call) throws SQLException, DataAccessException {
					call.execute();
					return call.getString(1);// 获取输出参数的值
				}
			});
		} catch (DataAccessException e) {
			logger.warn("未获 获取到单号 DataAccessException of orderNo===>");
			// logger.warn(e);
			throw e;
		}
		return orderNo;
	}

	@Override
	public SysRoler GetLoginUser() {
		SysRoler user = (SysRoler) SecurityUtils.getSubject().getPrincipal();
		if (user != null)
			return user;
		else
			return null;
	}

	// =========================按照命名参数封装的方法==========================

	@Override
	public List<Map<String, Object>> querySqlListByNamedParam(String sql, Map<String, Object> params) {
		List<Map<String, Object>> list = null;
		try {
			NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
			list = template.queryForList(sql, params);
		} catch (DataAccessException e) {
			try {
				String data = params == null ? "" : JsonUtils.serialize(params);
				logger.warn("未查询到期望数据(DataAccessException) of querySqlListByNamedParam===>" + sql + "参数:" + data);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// logger.warn(e);
		}
		return list;
	}

	@Override
	public Integer querySqlCountByNamedParam(String sql, Map<String, Object> params) {
		Integer i = 0;
		try {
			NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
			i = template.queryForObject(sql, params, Integer.class);
		} catch (DataAccessException e) {
			try {
				String data = params == null ? "" : JsonUtils.serialize(params);
				logger.warn("未查询到期望数据(DataAccessException) of querySqlCountByNamedParam===>" + sql + "参数:" + data);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// logger.warn(e);
		}
		return i;
	}

	@Override
	public Integer updateByNamedParam(String sql, Map<String, Object> params) {
		Integer i = 0;
		try {
			NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
			i = template.update(sql, params);
		} catch (DataAccessException e) {
			try {
				String data = params == null ? "" : JsonUtils.serialize(params);
				logger.warn("未 更新 到期望数据(DataAccessException) of updateByNamedParam===>" + sql + "参数:" + data);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// logger.warn(e);
			throw e;
		}
		return i;
	}

}
