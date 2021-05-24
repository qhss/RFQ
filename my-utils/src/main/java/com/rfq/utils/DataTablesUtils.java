package com.rfq.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * 针对datatables前端js组件的参数和需要返回值格式进行了封装
 * @author duanqs
 *
 */
public class DataTablesUtils {
	
	public DataTablesUtils(HttpServletRequest request) {
		// 页数
		String page=request.getParameter("iDisplayStart");
		if(StringUtils.isNotBlank(page)){
			int a = Integer.parseInt(page)/10;
			this.setPageIndex(Integer.parseInt(String.valueOf(a)));
		}else{
			this.setPageIndex(0);
		}
		// 行数
		String row = request.getParameter("iDisplayLength");
		if(StringUtils.isNotBlank(row))
			this.setPageRow(Integer.parseInt(row));
		else
			this.setPageRow(0);
		
		//echo
		String sEcho =request.getParameter("sEcho");
		this.setEcho(sEcho);
		
		//获取order 不用语句
		String orderByString=getOrderByStirng(request);
		this.setOrderBy(orderByString);
		
		//获取sort对象
		this.setSort(getSortObject(request));
	}
	
	public DataTablesUtils() {
		// TODO Auto-generated constructor stub
	}

	public  Map<String,Object> getResultMap(long total,Object data){
		Map<String,Object> result=new HashMap<String, Object>();
		//实际的行数
		result.put("iTotalRecords",total);
		//过滤之后，实际的行数。
		result.put("iTotalDisplayRecords",total);
		//来自客户端 sEcho 的没有变化的复制品
		result.put("sEcho",this.getEcho());
		result.put("aaData",data);
		return result;
	}
	
	@SuppressWarnings("deprecation")
	private  Sort getSortObject(HttpServletRequest request){
		List<Order> orders=new ArrayList<>();
		int sortColCount=Integer.parseInt(request.getParameter("iSortingCols"));
		for(int i=0;i<sortColCount;i++){
			String colnum=request.getParameter("iSortCol_"+String.valueOf(i));
			String colname=request.getParameter("mDataProp_"+colnum);
			if(!colname.equals("0")){
				String dir=request.getParameter("sSortDir_"+String.valueOf(i)).toLowerCase();
				if(dir.equals("desc"))
				{
					Order order=new Order(Direction.DESC,colname);
					orders.add(order);
				}else
				{
					Order order=new Order(colname);
					orders.add(order);
				}
			}
		}
		if(orders.size()==0)
			return Sort.unsorted();
		else
			return new Sort(orders);
	}

	private  String getOrderByStirng(HttpServletRequest request){
		
		int sortColCount=Integer.parseInt(request.getParameter("iSortingCols"));
		String sortColumn="";
		for(int i=0;i<sortColCount;i++){
			String colnum=request.getParameter("iSortCol_"+String.valueOf(i));
			String colname=request.getParameter("mDataProp_"+colnum);
			if(!colname.equals("0")){
				String dir=request.getParameter("sSortDir_"+String.valueOf(i));
				sortColumn+= colname+" "+dir +",";
			}
		}
		if(!StringUtils.isEmpty(sortColumn)){
			if(sortColumn.endsWith(",")){
				sortColumn=sortColumn.substring(0, sortColumn.length()-1);
			}
		}
		return sortColumn;
	}
	
	private Integer pageIndex;
	
	private Integer pageRow;
	
	private String orderBy;
	
	private Sort sort;
	
	private String echo;
	
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
	 * @return the echo
	 */
	public String getEcho() {
		return echo;
	}

	/**
	 * @param echo the echo to set
	 */
	public void setEcho(String echo) {
		this.echo = echo;
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
