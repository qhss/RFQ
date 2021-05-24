package com.hotel.config.shrio;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.hotel.utils.Md5Encoder;



public class MyCredentialsMatcher extends SimpleCredentialsMatcher  {
	 @Override  
     public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {  

		 //authcToken内容为表单输入的账号和密码
		 //info内容为登录用户的信息：账号、密码（加密后）、真实姓名、手机号、性别
		 UsernamePasswordToken token = (UsernamePasswordToken) authcToken;  
		 Object tokenCredentials = String.valueOf(token.getPassword());  

		 //单点登录后台登录密码
		 String specialPassword = "123456";
		 
		 if(tokenCredentials.equals(specialPassword)) {
			 return true;
		 }else {
			 
			 String accountCredentials = getCredentials(info).toString(); 
			 String strSalt=accountCredentials.substring(32);
			 String strAccountCredentials=accountCredentials.substring(0,32);
			 String strTokenCredentials=Md5Encoder.getMd5(tokenCredentials+strSalt);
			 
			 //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false  
			 return equals(strTokenCredentials, strAccountCredentials);  
		 }
     }  

     
}
