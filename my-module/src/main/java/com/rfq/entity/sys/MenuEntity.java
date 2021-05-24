package com.rfq.entity.sys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rfq.common.annotation.Disable;


public class MenuEntity implements Serializable {

	@Disable
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String url;
	private Integer pid;
	private String remarks;
	private Integer type;
	private String code;
	private Integer state;
	private String style;
	private Integer sort;
	private List<SysMenu> menus=new ArrayList<SysMenu>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public List<SysMenu> getMenus() {
		return menus;
	}
	public void setMenus(List<SysMenu> menus) {
		this.menus = menus;
	}
	
}
