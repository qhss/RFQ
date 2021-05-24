package com.rfq.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rfq.entity.sys.SysRoler;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.rfq.utils.DataTablesUtils;

public class BasicController {
	
	//需要先调用InitDataTablesParams才能获取值
	private Integer pageIndex;
	//需要先调用InitDataTablesParams才能获取值
	private Integer pageRow;
	//需要先调用InitDataTablesParams才能获取值
	private String orderBy;
	//需要先调用InitDataTablesParams才能获取值
	private Sort sort;
	
	/**
	 * 初始化datatables全端js组件参数值
	 * @param request
	 */
	public void InitDataTablesParams(HttpServletRequest request) {
		DataTablesUtils utils=new DataTablesUtils(request);
		this.setPageIndex(utils.getPageIndex());
		this.setPageRow(utils.getPageRow());
		this.setSort(utils.getSort());
		this.setOrderBy(utils.getOrderBy());
	}
	
	/**
	 * 获取datatables前端js组件需要的数据结构
	 * @param total
	 * @param data
	 * @return
	 */
	public Map<String , Object> getResultMap(long total,Object data){
		DataTablesUtils utils=new DataTablesUtils();
		return utils.getResultMap(total, data);
	}
	public <E> Map<String , Object> getResultMap (Page<E> result){
		DataTablesUtils utils=new DataTablesUtils();
		long total=result.getTotalElements();
		Object data=result.getContent();
		return utils.getResultMap(total, data);
	}
	/**
	 * 获取登陆用户对象
	 * @return User
	 */
	public SysRoler GetLoginUser(){
		SysRoler user =(SysRoler) SecurityUtils.getSubject().getPrincipal();
		if(user!=null)
			return user;
		else
			return null;
	}

	/**
	 * @return the pageIndex
	 */
	public Integer getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the pageRow
	 */
	public Integer getPageRow() {
		return pageRow;
	}

	/**
	 * @param pageRow the pageRow to set
	 */
	public void setPageRow(Integer pageRow) {
		this.pageRow = pageRow;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the sort
	 */
	public Sort getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	
	
}
