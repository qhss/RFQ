package com.rfq.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rfq.config.shrio.MyCredentialsMatcher;
import com.rfq.config.shrio.MySystemRealm;

@Configuration
public class ShiroConfiguration {
	private Logger logger = LogManager.getLogger(ShiroConfiguration.class);

	/**
	 * Filter Chain定义说明 1.一个URL可以配置多个Filter，使用逗号分隔 2.当设置多个过滤器时，全部验证通过，才视为通过
	 * 3.部分过滤器可指定参数，如perms，roles
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		logger.info("ShiroConfiguration.shirFilter()");

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 首先注入securityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 创建拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

		// 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");


		// authc:所有url都必须认证通过才可以访问;
		// anon:匿名拦截器，即不需要登录即可访问；一般用于静态资源过滤；示例“/static/**=anon”
		// authcBasic:Basic HTTP身份验证拦截器，主要属性： applicationName：弹出登录框显示的信息（application）
		// logout:退出拦截器，主要属性：redirectUrl：退出成功后重定向的地址（/）;示例“/logout=logout”
		// user:用户拦截器，用户已经身份验证/记住我登录的都可；示例“/**=user”
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**.*", "anon");
        filterChainDefinitionMap.put("/ztree/**", "anon");
		filterChainDefinitionMap.put("/assets/**", "anon");
		filterChainDefinitionMap.put("/img/**.*", "anon");
		filterChainDefinitionMap.put("/404", "anon");
		// 系统监控用的目录
		filterChainDefinitionMap.put("/favicon.ico", "anon");
		// 上传的图片
		filterChainDefinitionMap.put("/uploadFiles/**", "anon");
		// 接口直接访问
		filterChainDefinitionMap.put("/api/**", "anon");
		
		// 过滤链定义，从上向下顺序执行，一般将 /**放在下面,预设的拦截器如下:
		filterChainDefinitionMap.put("/**", "authc");

		// 设置login页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 设置登陆成功后跳转的页面
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 设置没权限操作的提示页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		// 加载过滤器
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	/**
	 * 注入 SecurityManager对象
	 * 
	 * @return
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置Realm
		securityManager.setRealm(myShiroRealm());
		return securityManager;
	}

	/**
	 * 注入身份认证realm myShiroRealm
	 * 
	 * @return
	 */
	@Bean
	public MySystemRealm myShiroRealm() {
		MySystemRealm userRealm = new MySystemRealm();
		// md5加密
		userRealm.setCredentialsMatcher(new MyCredentialsMatcher());
		return userRealm;
	}

	
	/**
	 * 开启shiro aop注解支持.使用代理方式;所以需要开启代码支持;
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 * 所以我们需要修改下doGetAuthenticationInfo中的代码; ）
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(0);// 散列的次数，相当于 md5(md5(pwd));
		return hashedCredentialsMatcher;
	}

	/**
	 * 为了在 thy模板页面使用 shiro标签
	 * 
	 * @return
	 */
	// @Bean
	// public ShiroDialect shiroDialect() {
	// return new ShiroDialect();
	// }
}
