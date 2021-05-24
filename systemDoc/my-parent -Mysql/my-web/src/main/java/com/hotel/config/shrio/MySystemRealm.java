package com.hotel.config.shrio;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.hotel.config.Config;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.Menu;
import com.hotel.entity.sys.Role;
import com.hotel.entity.sys.User;
import com.hotel.service.sys.MenuService;
import com.hotel.service.sys.RoleService;
import com.hotel.service.sys.UserService;
import com.hotel.util.MenuUtil;



public class MySystemRealm extends AuthorizingRealm {
	@SuppressWarnings("unused")
	private Logger logger = LogManager.getLogger(MySystemRealm.class);
	
	@Autowired
	UserService userService;

	@Autowired
	MenuService menuService;
	
	@Autowired
	RoleService roleService;
	
	
	@Autowired
	Config config;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		String currentLoginName = (String) principals.getPrimaryPrincipal();
		System.out.println(currentLoginName);
		List<String> userRoles = new ArrayList<String>();
		List<String> userPermissions = new ArrayList<String>();
		//从数据库中获取当前登录用户的详细信息
		
		ServiceResult<User> userResult=userService.queryUserByLoginName(currentLoginName);
		if(userResult!=null && userResult.isSuccess()) {
			User user=userResult.getData();
			//获取当前用户下拥有的所有角色列表
			ServiceResult<List<Role>> userRoleOption= roleService.queryRolesByUserId(user.getId());
			if(userRoleOption.isSuccess()) {
				for (Role role : userRoleOption.getData()) {
					userRoles.add(role.getName());
				}
			}
			//暂时用不着,先加一个固定值,需要时再实现
		} else {
			throw new AuthorizationException();
		}
		
		// 为当前用户设置角色和权限
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addRoles(userRoles);
		authorizationInfo.addStringPermissions(userPermissions);
		return authorizationInfo;
	}

	/**
	 * 验证当前登录的Subject LoginController.login()方法中执行Subject.login()时 执行此方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		

		String loginName = (String) authcToken.getPrincipal();		
		ServiceResult<User> userResult=userService.queryUserByLoginName(loginName);
		if(userResult==null)
			throw new UnknownAccountException();//没找到帐号
		
		User user=userResult.getData();
		//设置菜单
		String userId=user.getId();
		List<Menu> menus=menuService.queryMenu(userId);
		
		if(menus!=null)
			user.setHisMenus(new MenuUtil(menus).buildTree());
		
		SecurityUtils.getSubject().getSession().setAttribute("user",user);

		SimpleAuthenticationInfo authenticationInfo = 
				new SimpleAuthenticationInfo(
						user,
						user.getPassword()+user.getRandomStr(),
						ByteSource.Util.bytes(user.getRandomStr()),//salt
						user.getUserName()==null?"":user.getUserName() //realm name
						);
		
		return authenticationInfo;
	}
	
	
}
