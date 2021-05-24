package com.hotel.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hotel.entity.sys.User;
import com.hotel.utils.DingTalkUtil;
import com.hotel.utils.JsonUtils;



@ControllerAdvice
public class GlobalExceptionHandler {
		
	@Autowired
	Config config;

	
	@ExceptionHandler(Exception.class)
	public String defaultExceptionHandler(HttpServletRequest request,Exception e) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("message", e.getMessage());
		String traceInfo= ExceptionUtils.getStackTrace(e);
		if(!StringUtils.isEmpty(traceInfo)) {
			if(traceInfo.length()>500)
				traceInfo=traceInfo.substring(0, 500)+"...";
		}
		map.put("detail", traceInfo);
		request.setAttribute("message", e.getMessage());
		request.setAttribute("detail", traceInfo);
		String path= request.getServerName()
				+(request.getServerPort()==80?"":":"+((Integer)request.getServerPort()).toString())
				+request.getRequestURI() ;
		String param="";
		try {
			param= JsonUtils.serialize(request.getParameterMap());
		} catch (JsonGenerationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		User user=null;
		//404时无法使用SecurityUtils
		if(!e.getClass().getName().contains("UnavailableSecurityManagerException")) {
			if(SecurityUtils.getSubject()!=null)
				if(SecurityUtils.getSubject().getPrincipal()!=null)
					user =(User)SecurityUtils.getSubject().getPrincipal();
			
			
			String message="HIS系统出现异常\r网址:"+path+
					"\r用户:"+(user==null?"null":user.getLoginName())
						+"/"+(user==null?"null":user.getLoginName())+
					"\r参数:"+param+
					"\r错误信息:"+e.getMessage()+
					"\r错误详情:\r" +traceInfo;
			SendToDingTalk(message);
			
		}else
		{
			traceInfo="疑似网页无法找到导致(404)\r\n"+traceInfo;
			request.setAttribute("detail", traceInfo);
		}
		
        return "sys/error";
        
	}
		
		
	private void SendToDingTalk(String message) {
		Boolean iscan=config.getDingTalkEnable();
		if(iscan) {
			String token=config.getDingTalkToken();
			String atMobiles=config.getDingTalkAtMobiles();
			String atNames=config.getDingTalkAtNames();
			
			DingTalkUtil.SendMessage(token, message, atMobiles, atNames);
		}
	}
}
