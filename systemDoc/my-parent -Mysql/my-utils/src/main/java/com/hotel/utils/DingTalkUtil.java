package com.hotel.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


public class DingTalkUtil {

	private static Logger logger = LogManager.getLogger(DingTalkUtil.class);

	/**
	 * 向钉钉群机器人发送消息
	 * @param token
	 * @param message
	 * @param atMobiles
	 * @param atNames
	 */
	public static void SendMessage(String token,String message,String atMobiles,String atNames) {
		try {
			
			 RestTemplate restTemplate = new RestTemplate();
			 
			 message+=getAtMobileString(atNames);//添加at姓名
			 Map<String, Object> at=getAtMobiles(atMobiles); //添加at手机号码
			 HttpEntity<String> text=createMessage(message,at);
			 
			 System.out.println("钉钉发送消息"+message+"\n"+token);
			 restTemplate.postForEntity("https://oapi.dingtalk.com/robot/send?access_token="+token, 
					 text, Void.class);
			 
		} catch (Exception e) {
			//忽略所有错误,防止对业务的影响
			e.printStackTrace();
			logger.error(e);
		}
		
		 
	}
	
	
	@SuppressWarnings("unused")
	private static String getStackMsg(Exception e) {  
        StringBuffer sb = new StringBuffer();  
        StackTraceElement[] stackArray = e.getStackTrace();  
        for (int i = 0; i < stackArray.length; i++) {  
            StackTraceElement element = stackArray[i];  
            sb.append(element.toString() + "\n");  
        }  
        return sb.toString();  
    }
	
	private static  HttpEntity<String> createMessage(String message, Map<String, Object> at) {
		try {
	        Map<String, Object> messageJson = new HashMap<>();
	        Map<String, Object> context = new HashMap<>();
	        context.put("content", message);
	        String json=JsonUtils.serialize(context);
	        messageJson.put("text", json);
	        if(at!=null)
	        	messageJson.put("at", at);
	        messageJson.put("msgtype", "text");
	        
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	        
	        String jsonMessage=JsonUtils.serialize(messageJson);
	        return new HttpEntity<String>(jsonMessage, headers);
		}catch (Exception e) {
			return null;
		}
    }
	
	/**
     * at 的手机号码(钉钉号)
     * @return
     */
    private static Map<String, Object> getAtMobiles(String ms) {
    	Map<String, Object> mobiles=new HashMap<String,Object>();
    	
    	if(!ms.isEmpty()) {
    		List<String> mlist=new ArrayList<String>() ;
    		for (String m : ms.split(",")) {
				if(!m.isEmpty())
					mlist.add(m);
			}
    		mobiles.put("atMobiles",mlist);
        	mobiles.put("isAtAll", false);
        	return mobiles;
    	}
    	return null;
    }
    
    /**
     * at 的人员姓名
     * @return
     */
    private static String getAtMobileString(String names) {
    	String result="";
    	if(!names.isEmpty()) {
    		for (String m : names.split(",")) {
    			if(!m.isEmpty())
    				result+=" @"+m+" ";
			}
    		return result;
    	}
    	return "";
    }
}
