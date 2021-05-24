package com.hotel.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hotel.config.Config;
import com.hotel.service.sys.UserService;
import com.hotel.utils.DingTalkUtil;




@Controller
@RequestMapping("/")
public class RootController extends BasicController {

	private Logger logger = LogManager.getLogger(RootController.class);

	@Resource
	Config config;
	
	@Resource
	UserService userService;


	/**
	 * 发送运营消息
	 * @param message
	 */
	public void SendOperationMessage(String message) {
		String token=config.getDingTalkOperationToken();
		if(!StringUtils.isEmpty(token)) {
			
			DingTalkUtil.SendMessage(token, message, "", "");
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Map<String, Object> map) {
		SecurityUtils.getSubject().logout();
		//附加下载路径
		map.put("dlHost", config.getImgHttpHost());
		
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Map<String, Object> map) {
		return "login";
	}

	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Map<String, Object> map) throws Exception {
		logger.info("BaseController.login()");
		// 登录失败从request中获取shiro处理的异常信息。
		// shiroLoginFailure:就是shiro异常类的全类名.
		String exception = (String) request.getAttribute("shiroLoginFailure");

		logger.info("登陆异常 exception=" + exception);
		String msg = "";
		if (exception != null) {
			if (UnknownAccountException.class.getName().equals(exception)) {
				msg = "账号不存在.";
			} else if (IncorrectCredentialsException.class.getName().equals(exception)) {
				msg = "密码错误";
			} else if ("kaptchaValidateFailed".equals(exception)) {
				msg = "验证码错误";
			} else {
				msg = "未知异常:" + exception;
			}
		}else
			return "index";
		
		map.put("msg", msg);

//		String hospitalId =(String)request.getSession().getAttribute("hospitalId");
//		if(null!=hospitalId) {//如果是医馆登录入口 返回输入医馆账号成功后的 登录账号密码界面  
//			request.setAttribute("hospitalNames", (String)request.getSession().getAttribute("hospitalName"));
//			return "hospitalLogin";
//		}
		
		return "login";
	}
	
	
	
	
	@RequestMapping(value= {"/","/index"})
	public String Home( Map<String, Object> map) {
		
		return "index";
	}
	
	
	
	
}
