package com.hotel.service.sys;

import java.util.List;

import com.hotel.common.BaseServiceInterface;
import com.hotel.entity.sys.Menu;

/**
 * 菜单
 */

public interface MenuService extends BaseServiceInterface{

	
	/**
	 * 添加或新增
	 * @param role
	 * @return
	 */
	public abstract Object addOrUpdate(Menu menu);
	
	
	/**
	 * 查询菜单
	 * @param userId
	 * @return
	 */
	public abstract List<Menu> queryMenu(String userId);
}
