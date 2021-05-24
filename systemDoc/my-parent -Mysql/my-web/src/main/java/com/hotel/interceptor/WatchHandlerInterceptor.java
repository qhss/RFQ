package com.hotel.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hotel.config.Config;

@Component
public class WatchHandlerInterceptor implements HandlerInterceptor {
	
	private Logger logger = LogManager.getLogger(WatchHandlerInterceptor.class);
	
	@Resource
	Config config;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal("StopWatch-StartTime");

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		long beginTime = System.currentTimeMillis();
		this.startTimeThreadLocal.set(Long.valueOf(beginTime));
		
		return true;
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long endTime = System.currentTimeMillis();
		long beginTime = ((Long) this.startTimeThreadLocal.get()).longValue();
		long consumeTime = endTime - beginTime;
		if (consumeTime > 1000L) {
			this.logger.info("请求耗费时间太长了：" + String.format("%s 消耗  %d 秒",
					new Object[] { request.getRequestURI(), Long.valueOf(consumeTime)/1000 }));
		}
		if (consumeTime > 3000L) {
			try {
//				User user=null;
//				if(SecurityUtils.getSubject()!=null)
//					if(SecurityUtils.getSubject().getPrincipal()!=null)
//						user =(User)SecurityUtils.getSubject().getPrincipal();
				
//				String message="请求耗费时间太长了：" + 
//						String.format("%s 消耗  %d 秒",
//						new Object[] { request.getRequestURL().toString(), 
//								Long.valueOf(consumeTime)/1000 });
//				
//				message+="\r用户:"+(user==null?"null":user.getLoginName())
//					+"/"+(user==null?"null":user.getLoginName());
				 
				//DingTalkUtil.SendMessage(config.getDingTalkToken(), message,"","");
				
			}catch(Exception e) {
				//
			}
		} 
		
	}

}
