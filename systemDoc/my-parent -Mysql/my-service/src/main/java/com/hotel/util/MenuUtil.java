package com.hotel.util;

import java.util.ArrayList;
import java.util.List;

import com.hotel.entity.sys.Menu;
import com.hotel.entity.sys.MenuEntity;


public class MenuUtil {

	private StringBuffer html = new StringBuffer();
	private List<Menu> menus;

	public MenuUtil(List<Menu> menus) {
		this.menus = menus;
	}

	public  String buildTreeForOne() {
		html.append("<ul class=\"sidebar-nav\">");
		for (Menu menu : menus) {
			Integer id = menu.getId();
			if (menu.getPid()== null||menu.getPid()== 0) {
				if(0==getChildren(menu).size()){
					//一级菜单没有子菜单
					html.append("<li class=\"sidebar-nav-link\">");
					html.append("<a href=\""+menu.getUrl().replace(".action", "")+"\" class=\"active\"><i class=\""+menu.getStyle()+" sidebar-nav-link-logo\"></i>"+menu.getName()+"</a>");
					html.append("</li>");
				}else{
					//一级菜单有子菜单
					html.append("<li class=\"sidebar-nav-link\">");
					html.append("<a href=\"javascript:;\" class=\"sidebar-nav-sub-title\" data-am-collapse=\"{target: '#collapse-"+id+"'}\">");
					html.append("	<i class=\""+menu.getStyle()+" sidebar-nav-link-logo\"></i>"+menu.getName());
					html.append("	<span class=\"am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico\"></span>");
					html.append("</a>");
					
					buildSubForOne(menu,id);
				}
				
			}
		}
		html.append("</ul>");
		return html.toString();
	}
	
	private void buildSubForOne(Menu menu,Integer pid) {
		List<Menu> children = getChildren(menu);
		if (!children.isEmpty()) {
			html.append("<ul id='collapse-"+pid+"' class=\"sidebar-nav sidebar-nav-sub am-collapse\">");
			for (Menu child : children) {
				Integer id = child.getId();
				if(0==getChildren(child).size()){
					html.append("<li class=\"sidebar-nav-link\">");
					html.append("  <a href=\""+child.getUrl().replace(".action", "")+"\">");
					html.append("	 <span class=\""+child.getStyle()+" sidebar-nav-link-logo\"></span>"+child.getName());
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
		for (Menu menu : menus) {
			Integer id = menu.getId();
			if (menu.getPid()== null||menu.getPid()== 0) {
				if(0==getChildren(menu).size()){
					//一级菜单没有子菜单
					html.append("<li>");
					html.append("<a href='"+menu.getUrl().replace(".action", "")+"' class=\"am-cf am-c\" id='menu_"+id+"'><span class='"+menu.getStyle()+"'></span>"+menu.getName()+"<span class=\" am-fr am-margin-right\"></span></a>");
					html.append("</li>");
				}else{
					//一级菜单有子菜单
					html.append("<li class=\"admin-parent\">");
					html.append("<a class=\"am-cf\" data-am-collapse=\"{target: '#collapse-"+id+"'}\"><span class='"+menu.getStyle()+"'></span>"+menu.getName()+"<span class=\"am-icon-angle-right am-fr am-margin-right\"></span></a>");
					build(menu,id);
				}
				
			}
		}
		html.append("\r\n</ul></div>");
		return html.toString();
	}

	private void build(Menu menu,Integer pid) {
		List<Menu> children = getChildren(menu);
		if (!children.isEmpty()) {
			html.append("\r\n<ul class=\"am-list am-collapse admin-sidebar-sub\" id=\"collapse-"+pid+"\">");
			for (Menu child : children) {
				Integer id = child.getId();
				if(0==getChildren(child).size()){
					html.append("<li>");
					html.append("<a href='"+child.getUrl().replace(".action", "")+"' class=\"am-cf am-c\" id='menu_"+id+"'><span class='"+child.getStyle()+"'></span>"+child.getName()+"<span class=\" am-fr am-margin-right\"></span></a>");
					html.append("</li>");
				}else{
					html.append("<li class=\"admin-parent\">");
					html.append("<a class=\"am-cf\" data-am-collapse=\"{target: '#collapse-"+id+"'}\"><span class='"+child.getStyle()+"'></span>"+child.getName()+"<span class=\"am-icon-angle-right am-fr am-margin-right\"></span></a>");
					build(child,id);
				}
			}
			html.append("\r\n</ul>");
		}
		
	}

	private List<Menu> getChildren(Menu menu) {
		List<Menu> children = new ArrayList<Menu>();
		Integer id = menu.getId();
		for (Menu child : menus) {
			if (null!=child&&id.equals(child.getPid())) {
				children.add(child);
			}
		}
		return children;
	}
	
	public  List<MenuEntity> buildAppMenu(){
		List<MenuEntity> menuList = new ArrayList<MenuEntity>();
		
		for (Menu menu : menus) {
			if (menu.getPid()== null||menu.getPid()== 0) {
				MenuEntity menuEntity = new MenuEntity();
				menuEntity.setId(menu.getId());
				menuEntity.setName(menu.getName());
				menuEntity.setUrl(menu.getUrl());
				menuEntity.setPid(menu.getPid());
				menuEntity.setRemarks(menu.getRemarks());
				menuEntity.setType(menu.getType());
				menuEntity.setCode(menu.getCode());
				menuEntity.setState(menu.getState());
				menuEntity.setStyle(menu.getStyle());
				menuEntity.setSort(menu.getSort());
				List<Menu> childrenMenus = getChildren(menu);
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
