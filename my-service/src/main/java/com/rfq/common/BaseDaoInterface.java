package com.rfq.common;

import com.rfq.entity.sys.SysRoler;

import java.util.List;
import java.util.Map;

/**
 * 2016-6-16
 */
public abstract interface BaseDaoInterface {

	/**
	 * 根据实体查询
	 * 
	 * @param entity 实体
	 * @return
	 */
	public abstract <T> List<T> query(T entity, boolean... isChild);

	/**
	 * 根据类名和条件查询
	 * 
	 * @param t
	 * @return
	 */
	public abstract <T> List<T> query(Class<T> t, Map<String, Object> map, boolean... isChild);

	/**
	 * 根据类名和条件查询
	 * 
	 * @param t
	 * @return
	 */
	public abstract <T> T queryOne(Class<T> t, Map<String, Object> map, boolean... isChild);

	/**
	 * 根据实体查询单个
	 * 
	 * @param entity 实体
	 * @return
	 */
	public abstract <T> T queryByEntity(T entity, boolean... isChild);

	/**
	 * 根据id查询单个
	 * 
	 * @param id id
	 * @return
	 */
	public abstract <T> T queryById(Class<T> t, Object id, boolean... isChild);

	/**
	 * 分页
	 * 
	 * @param entity 实体 T 参数对应 页数 行数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract <T> Map<String, Object> queryListByPage(T entity, T... i);

	/**
	 * 分页
	 * 
	 * @param entity 实体 T 参数对应 页数 行数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract <T> Integer queryListByPageNum(T entity, T... i);

	/**
	 * 分页
	 * 
	 * @param entity 实体 T 参数对应 页数 行数
	 * @return
	 */
	public abstract <T> Map<String, Object> queryListByPage(Class<T> t, Map<String, Object> param, boolean... isChild);

	/**
	 * 分页 总条数
	 * 
	 * @param entity
	 * @param param
	 * @return
	 */
	public abstract <T> Integer queryListByPageNum(Class<T> entity, Map<String, Object> param);

	/**
	 * 修改必须有ID 根据ID
	 */
	public abstract <T> Integer updateEntity(T entity);

	/**
	 * 修改必须有ID 根据ID
	 */
	public abstract <T> Integer updateOrAdd(T entity);

	/**
	 * 删除 根据Id
	 */
	public abstract <T> Integer deleteEntity(Class<T> entity, Object id);

	/**
	 * 批量删除 根据Id
	 */
	public abstract <T> Integer deleteEntityList(Class<T> entity, List<Object> ids);

	/**
	 * 增加
	 */
	public abstract <T> Object addEntity(T entity);

	/**
	 * 增加
	 */
	public abstract <T> Object addEntityUUID(T entity);

	/**
	 * 批量增加
	 */
	public abstract <T> Object addEntity(List<T> entitys);

	/**
	 * 是否存在 返回数量
	 * 
	 * @param <T>
	 */
	public abstract <T> Integer exist(Class<T> t, Map<String, Object> map);

	/**
	 * 是否存在不是本id 返回数量
	 * 
	 * @param <T>
	 */
	public abstract <T> Integer existId(Class<T> t, Map<String, Object> map);

	/**
	 * 批量修改
	 * 
	 * @param <T>
	 */
	public abstract <T> Integer batchUpdate(List<T> entitys);

	/**
	 * 自定义sql 查询数量
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public abstract Integer queryBysqlCount(String sql, Object[] obj);
	
	/**
	 * 自定义sql 查询数量
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public abstract Double queryBysqlCountDouble(String sql, Object[] obj);

	/**
	 * 自定义sql 查询列表List<Map<String,Object>>
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public abstract List<Map<String, Object>> queryBysqlList(String sql, Object[] obj);

	/**
	 * 自定义sql 查询列表List<Bean>
	 * 
	 * @param     <T>
	 * @param sql
	 * @param obj
	 * @return
	 */
	public abstract <T> List<T> queryBysqlListBean(Class<T> t, String sql, Object[] obj);

	/**
	 * 自定义sql 查询列表Map<String,Object>
	 * 
	 * @param     <T>
	 * @param sql
	 * @param obj
	 * @return
	 */
	public abstract Map<String, Object> queryBysqlMap(String sql, Object[] obj);

	/**
	 * 自定义sql 修改/增加 返回1成功 0失败
	 * 
	 * @param     <T>
	 * @param sql
	 * @param obj
	 * @return
	 */
	public abstract Integer update(String sql, Object[] obj);

	/**
	 * 自定义sql 删除 返回1成功 0失败
	 * 
	 * @param     <T>
	 * @param sql
	 * @param obj
	 * @return
	 */
	public abstract Integer delete(String sql, Object[] obj);

	/**
	 * 自定义sql 批量删除 返回成功数
	 * 
	 * @param     <T>
	 * @param sql
	 * @param obj
	 * @return
	 */
	public abstract Integer deleteBatch(String sql, List<Object> list);

	public abstract List<Map<String, Object>> querySqlListByNamedParam(String sql, Map<String, Object> params);

	/**
	 * 执行存储过程,无返回值
	 **/
	void execute(String sql);

	/**
	 * 生成订单号
	 * 
	 * @param orderMap
	 * @return
	 */
	public String orderNo();;

	public abstract String queryStringValue(String sql, Object[] obj);

	/**
	 * 获取当前登陆用户
	 * 
	 * @return
	 */
	public abstract SysRoler GetLoginUser();

	Integer querySqlCountByNamedParam(String sql, Map<String, Object> params);

	Integer updateByNamedParam(String sql, Map<String, Object> params);

	
	// public abstract String queryStringValue(String sql, Object[] obj, Integer
	// groupConcatMaxLen_K);

}
