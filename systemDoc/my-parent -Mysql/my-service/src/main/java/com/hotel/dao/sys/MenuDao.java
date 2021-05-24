package com.hotel.dao.sys;

import java.util.List;
import java.util.Map;

import com.hotel.common.BaseDaoInterface;
import com.hotel.entity.sys.Menu;

/**
 * 
 */
public interface MenuDao extends BaseDaoInterface {

	/**
	 * 查询菜单
	 * 
	 * @param userId
	 * @return
	 */
	public abstract List<Menu> queryMenu(String userId);

	List<Map<String, Object>> queryMenuOfRole(String hospitalId);
}
