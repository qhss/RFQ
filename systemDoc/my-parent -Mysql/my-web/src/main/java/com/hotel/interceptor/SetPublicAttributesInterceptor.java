package com.hotel.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hotel.config.Config;

@Component
public class SetPublicAttributesInterceptor implements HandlerInterceptor {

	@SuppressWarnings("unused")
	private Logger logger = LogManager.getLogger(SetPublicAttributesInterceptor.class);

	@Autowired
	Config config;


	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		request.setAttribute("contextPath", basePath);
//		if(config.getImUIUrl()!=null) {
//			if(!config.getImUIUrl().equals("")) {
//				request.setAttribute("imUIUrl", config.getImUIUrl());
//				request.setAttribute("imUICSS", config.getImUIUrl()+"/layui/css/layui.css");
//				request.setAttribute("imUIJS", config.getImUIUrl()+"/layui/layui.js");
//				request.setAttribute("imUICommonJS", config.getImUIUrl()+"/js/imCommon.js");
//				request.setAttribute("imServerUrl",config.getImServerUrl());
//				request.setAttribute("imgHost",config.getImgHttpHost());
//			}
//		}
//		if(config.getTheme()!=null) {
//			if(!org.apache.commons.lang.StringUtils.isEmpty(config.getTheme())) {
//				request.setAttribute("theme", config.getTheme());
//			}else
//				request.setAttribute("theme","");
//		}else
//			request.setAttribute("theme","");
//		
		return true;
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
