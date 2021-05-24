package com.rfq.config.shrio;

import java.util.List;

import com.rfq.dao.sys.SysMenuRepository;
import com.rfq.entity.sys.*;
import com.rfq.service.sys.SysRolerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.rfq.config.Config;
import com.rfq.entity.ServiceResult;
import com.rfq.util.MenuUtil;


public class MySystemRealm extends AuthorizingRealm {
	@SuppressWarnings("unused")
	private Logger logger = LogManager.getLogger(MySystemRealm.class);
	
	@Autowired
	SysRolerService userService;

	@Autowired
	SysMenuRepository sysMenuRepository;


	
	@Autowired
	Config config;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
//		String currentLoginName = (String) principals.getPrimaryPrincipal();
//		System.out.println(currentLoginName);
//		List<String> userRoles = new ArrayList<String>();
//		List<String> userPermissions = new ArrayList<String>();
//		//从数据库中获取当前登录用户的详细信息
//
//		ServiceResult<SysRoler> userResult=userService.queryUserByLoginId(currentLoginName);
//		if(userResult!=null && userResult.isSuccess()) {
//			SysRoler user=userResult.getData();
//			//获取当前用户下拥有的所有角色列表
//			ServiceResult<List<Role>> userRoleOption= roleService.queryRolesByUserId(user.getId());
//			if(userRoleOption.isSuccess()) {
//				for (Role role : userRoleOption.getData()) {
//					userRoles.add(role.getName());
//				}
//			}
//			//暂时用不着,先加一个固定值,需要时再实现
//		} else {
//			throw new AuthorizationException();
//		}
//
//		// 为当前用户设置角色和权限
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//		authorizationInfo.addRoles(userRoles);
//		authorizationInfo.addStringPermissions(userPermissions);
		return authorizationInfo;
	}

	/**
	 * 验证当前登录的Subject LoginController.login()方法中执行Subject.login()时 执行此方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		String loginName = (String) authcToken.getPrincipal();		
		ServiceResult<SysRoler> userResult=userService.queryUserByLoginId(loginName);
		if(userResult==null)
			throw new UnknownAccountException();//没找到帐号

		SysRoler user=userResult.getData();

		String copys = user.getCopy()+ "0000.";

//		String copys = user.getCopy().substring(0,user.getCopy().length()-1);

		List<SysMenu> menus = sysMenuRepository.queryMenus(copys);

		
		if(menus!=null)
			user.setHisMenus(new MenuUtil(menus).buildTree());
		
		SecurityUtils.getSubject().getSession().setAttribute("user",user);

		SimpleAuthenticationInfo authenticationInfo = 
				new SimpleAuthenticationInfo(
						user,
						user.getLoginPwd(),
//						ByteSource.Util.bytes(user.getRandomStr()),//salt
						user.getLoginId()==null?"":user.getLoginId() //realm name
						);
		
		return authenticationInfo;
	}
	
	
}
