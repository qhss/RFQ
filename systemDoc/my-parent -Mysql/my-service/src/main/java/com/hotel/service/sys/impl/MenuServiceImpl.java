package com.hotel.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotel.common.impl.BaseServiceImpl;
import com.hotel.dao.sys.MenuDao;
import com.hotel.entity.sys.Menu;
import com.hotel.service.sys.MenuService;


/**
 * 菜单
 */
@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl implements MenuService{

	
	@Resource(name="menuDao")
	MenuDao menuDao;
	
	@Override
	public List<Menu> queryMenu(String userId) {
		return menuDao.queryMenu(userId);
	}
	
	@Override
	public Object addOrUpdate(Menu menu) {
		if(null==menu){
			return 0;
		}
		if(null==menu.getId()||0==menu.getId()){
			return menuDao.addEntity(menu);
		}else{
			return menuDao.updateEntity(menu);
		}
	}

}
