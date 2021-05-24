package com.rfq.common.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.rfq.entity.sys.SysRoler;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rfq.common.BaseDaoInterface;
import com.rfq.common.BaseServiceInterface;


/**
 * service抽象类
 */
@Service("baseService")
public class  BaseServiceImpl implements BaseServiceInterface{
	
	//private Logger logger = LogManager.getLogger(BaseServiceImpl.class);
	
	@Resource(name="baseDao")
	BaseDaoInterface baseDao;


	
	/**
	 * 根据实体查询
	 * @param entity 实体
	 * @return
	 */
	public <T> List<T> query(T entity,boolean...isChild){
		return baseDao.query(entity,isChild);
	}
	
	
	/**
	 * 根据实体查询单个
	 * @param entity 实体
	 * @return
	 */
	public <T> T queryByEntity(T entity,boolean...isChild){
		return baseDao.queryByEntity(entity,isChild);
	}
	
	/**
	 * 根据id查询单个
	 * @param
	 * @return
	 */
	public <T> T queryById(Class<T> t ,Object id,boolean...isChild){
		return baseDao.queryById(t, id,isChild);
	}
	
	/**
	 * 分页
	 * @param entity 实体
	 * @return
	 */
	public <T> Map<String,Object> queryListByPage(T entity,@SuppressWarnings("unchecked") T... i){
		return baseDao.queryListByPage(entity, i);
	}
	

	
	
	/**
	 * 修改
	 */
	@Transactional(rollbackFor=Exception.class)
	public <T> Integer updateEntity(T entity){
		return baseDao.updateEntity(entity);
	}
	
	/**
	 * 删除 根据参数
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor=Exception.class)
	public <T> Map<String,Object> deleteEntity(T entity,T... t){
		return null;
	}
	
	/**
	 * 删除
	 */
	@Transactional(rollbackFor=Exception.class)
	public <T> Integer deleteEntity(Class<T> entity,Object id){
		return baseDao.deleteEntity(entity, id);
	}
	
	
	/**
	 * 增加
	 */
	@Transactional(rollbackFor=Exception.class)
	public <T> Object addEntity(T entity){
		return baseDao.addEntity(entity);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public <T> Object addEntityUUID(T entity){
		return baseDao.addEntityUUID(entity);
	}
	
	@Override
	public <T> Integer exist(Class<T> t, Map<String, Object> map) {
		return baseDao.exist(t, map);
	}
	
	@Override
	public <T> Integer existId(Class<T> t, Map<String, Object> map) {
		return baseDao.existId(t, map);
	}
	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public <T> Integer batchUpdate(List<T> entitys) {
		return baseDao.batchUpdate(entitys);
	}
	
	
	@Override
	public <T> Integer deleteEntityList(Class<T> entity, List<Object> ids) {
		return baseDao.deleteEntityList(entity, ids);
	}


	@Override
	public <T> List<T> query(Class<T> t, Map<String, Object> map,boolean...isChild) {
		return baseDao.query(t, map,isChild);
	}

	@Override
	public <T> T queryOne(Class<T> t, Map<String, Object> map,boolean...isChild) {
		return baseDao.queryOne(t, map,isChild);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Integer queryListByPageNum(T entity, T... i) {
		return baseDao.queryListByPageNum(entity, i);
	}

	@Override
	public <T> Map<String, Object> queryListByPage(Class<T> t,
			Map<String, Object> param,boolean...isChild) {
		return baseDao.queryListByPage(t, param,isChild);
	}

	@Override
	public <T> Integer queryListByPageNum(Class<T> entity,
			Map<String, Object> param) {
		return baseDao.queryListByPageNum(entity, param);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public <T> Integer updateOrAdd(T entity) {
		return baseDao.updateOrAdd(entity);
	}


	@Override
	public Integer queryBysqlCount(String sql, Object[] obj) {
		return baseDao.queryBysqlCount(sql, obj);
	}


	@Override
	public List<Map<String, Object>> queryBysqlList(String sql, Object[] obj) {
		return baseDao.queryBysqlList(sql, obj);
	}

	@Override
	public <T> List<T> queryBysqlListBean(Class<T> t, String sql, Object[] obj) {
		return baseDao.queryBysqlListBean(t, sql, obj);
	}


	@Override
	public Map<String, Object> queryBysqlMap(String sql, Object[] obj) {
		return baseDao.queryBysqlMap(sql, obj);
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public Integer update(String sql, Object[] obj) {
		return baseDao.update(sql, obj);
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public Integer delete(String sql, Object[] obj) {
		return baseDao.delete(sql, obj);
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public Integer deleteBatch(String sql, List<Object> list) {
		return baseDao.deleteBatch(sql, list);
	}
	
	
	
	public String GetStackInfo(Exception e) {  
        StringBuffer sb = new StringBuffer();  
        sb.append("Message:"+e.getMessage()+"\r\n StackTrace:");
        StackTraceElement[] stackArray = e.getStackTrace();  
        for (int i = 0; i < stackArray.length; i++) {  
            StackTraceElement element = stackArray[i];  
            sb.append(element.toString() + "\r\n");  
        }  
        return sb.toString();  
    } 
	
	
	@Override
	public SysRoler GetLoginUser(){
		SysRoler user =(SysRoler) SecurityUtils.getSubject().getPrincipal();
		if(user!=null)
			return user;
		else
			return null;
	}
	
}
