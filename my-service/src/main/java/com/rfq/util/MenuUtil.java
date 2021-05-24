package com.rfq.util;

import java.util.ArrayList;
import java.util.List;

import com.rfq.entity.sys.MenuEntity;
import com.rfq.entity.sys.SysMenu;


public class MenuUtil {

	private StringBuffer html = new StringBuffer();
	private List<SysMenu> menus;

	public MenuUtil(List<SysMenu> menus) {
		this.menus = menus;
	}

	public  String buildTreeForOne() {
		html.append("<ul class=\"sidebar-nav\">");
		for (SysMenu menu : menus) {
			Integer id = menu.getId();
			if (menu.getParentId()== null||menu.getParentId()== 0) {
				if(0==getChildren(menu).size()){
					//一级菜单没有子菜单
					html.append("<li class=\"sidebar-nav-link\">");
					html.append("<a href=\""+menu.getUrl().replace(".action", "")+"\" class=\"active\"><i class=\""+" sidebar-nav-link-logo\"></i>"+menu.getDisplay()+"</a>");
					html.append("</li>");
				}else{
					//一级菜单有子菜单
					html.append("<li class=\"sidebar-nav-link\">");
					html.append("<a href=\"javascript:;\" class=\"sidebar-nav-sub-title\" data-am-collapse=\"{target: '#collapse-"+id+"'}\">");
					html.append("	<i class=\""+" sidebar-nav-link-logo\"></i>"+menu.getDisplay());
					html.append("	<span class=\"am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico\"></span>");
					html.append("</a>");
					
					buildSubForOne(menu,id);
				}
				
			}
		}
		html.append("</ul>");
		return html.toString();
	}
	
	private void buildSubForOne(SysMenu menu,Integer pid) {
		List<SysMenu> children = getChildren(menu);
		if (!children.isEmpty()) {
			html.append("<ul id='collapse-"+pid+"' class=\"sidebar-nav sidebar-nav-sub am-collapse\">");
			for (SysMenu child : children) {
				Integer id = child.getId();
				if(0==getChildren(child).size()){
					html.append("<li class=\"sidebar-nav-link\">");
					html.append("  <a href=\""+child.getUrl().replace(".action", "")+"\">");
					html.append("  </a>");
					html.append("</li>");
				}else{
					buildSubForOne(child,id);
				}
			}
			html.append("</ul>");
		}
		
	}
	
	public  String buildTree() {
		html.append("<div id='leftmenuDiv'><ul class=\"am-list admin-sidebar-list\">");
		for (SysMenu menu : menus) {
			Integer id = menu.getId();
			if (menu.getParentId()== null||menu.getParentId()== 0) {
				if(0==getChildren(menu).size()){
					//一级菜单没有子菜单
					html.append("<li>");
					html.append("<a href='"+menu.getUrl().replace(".action", "")+"' class=\"am-cf am-c\" id='menu_"+id+"'><span class='"+"'></span>"+menu.getDisplay()+"<span class=\" am-fr am-margin-right\"></span></a>");
					html.append("</li>");
				}else{
					//一级菜单有子菜单
					html.append("<li class=\"admin-parent\">");
					html.append("<a class=\"am-cf\" data-am-collapse=\"{target: '#collapse-"+id+"'}\"><span class='"+"'></span>"+menu.getDisplay()+"<span class=\"am-icon-angle-right am-fr am-margin-right\"></span></a>");
					build(menu,id);
				}
				
			}
		}
		html.append("\r\n</ul></div>");
		return html.toString();
	}

	private void build(SysMenu menu,Integer pid) {
		List<SysMenu> children = getChildren(menu);
		if (!children.isEmpty()) {
			html.append("\r\n<ul class=\"am-list am-collapse admin-sidebar-sub\" id=\"collapse-"+pid+"\">");
			for (SysMenu child : children) {
				Integer id = child.getId();
				if(0==getChildren(child).size()){
					html.append("<li>");
					html.append("<a href='"+child.getUrl().replace(".action", "")+"' class=\"am-cf am-c\" id='menu_"+id+"'>"+child.getDisplay()+"<span class=\" am-fr am-margin-right\"></span></a>");
					html.append("</li>");
				}else{
					html.append("<li class=\"admin-parent\">");
					html.append("<a class=\"am-cf\" data-am-collapse=\"{target: '#collapse-"+id+"'}\">"+child.getDisplay()+"<span class=\"am-icon-angle-right am-fr am-margin-right\"></span></a>");
					build(child,id);
				}
			}
			html.append("\r\n</ul>");
		}
		
	}

	private List<SysMenu> getChildren(SysMenu menu) {
		List<SysMenu> children = new ArrayList<SysMenu>();
		Integer id = menu.getId();
		for (SysMenu child : menus) {
			if (null!=child&&id.equals(child.getParentId())) {
				children.add(child);
			}
		}
		return children;
	}
	
	public  List<MenuEntity> buildAppMenu(){
		List<MenuEntity> menuList = new ArrayList<MenuEntity>();
		
		for (SysMenu menu : menus) {
			if (menu.getParentId()== null||menu.getParentId()== 0) {
				MenuEntity menuEntity = new MenuEntity();
				menuEntity.setId(menu.getId());
				menuEntity.setName(menu.getDisplay());
				menuEntity.setUrl(menu.getUrl());
				menuEntity.setPid(menu.getParentId());
				menuEntity.setRemarks("");
				menuEntity.setType(menu.getType());
				menuEntity.setCode("");
				menuEntity.setState(0);
				menuEntity.setStyle("");
				menuEntity.setSort(menu.getOrderId());
				List<SysMenu> childrenMenus = getChildren(menu);
				if(0==childrenMenus.size()){
					//一级菜单没有子菜单
					menuList.add(menuEntity);
				}else{
					//一级菜单有子菜单
					menuEntity.setMenus(childrenMenus);
					menuList.add(menuEntity);
				}
			}
		}
		return menuList;
	}
}
