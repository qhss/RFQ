package com.hotel.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	/** 
	    * 将json格式的字符串解析成Map对象 <li> 
	    * json格式：{"name":"admin","retries":"3fff","testname" 
	    * :"ddd","testretries":"fffffffff"} 
	    */  
	public static Map<String, Object> jsonToMap(Object object)  
	   {  
	       Map<String, Object> data = new HashMap<String, Object>();  
	       // 将json字符串转换成jsonObject  
	       JSONObject jsonObject = JSONObject.fromObject(object);  
	       Iterator<?> it = jsonObject.keys();  
	       // 遍历jsonObject数据，添加到Map对象  
	       while (it.hasNext())  
	       {  
	           String key = String.valueOf(it.next());  
	           Object value =jsonObject.get(key);
	           if(value instanceof JSONArray){
	        	   value = parseJSON2List(value);
	           }
	           data.put(key, value);  
	       }  
	       return data;  
	   }  
	 /**
     * 
    * json转换list.
    * <br>详细说明
    * @param jsonStr json字符串
    * @return
    * @return List<Map<String,Object>> list
    * @throws
     */
    @SuppressWarnings("unchecked")
	public static List<Map<String, Object>> parseJSON2List(Object jsonObj){
    	String jsonStr=jsonObj.toString();
    	if(jsonStr.indexOf("{\"")<0)
	    	jsonStr=jsonStr
	    			.replace(" ", "")
					.replace("=", "\"=\"")
					.replace(",", "\",\"")
					.replace("{", "{\"").replace("}", "\"}")
					.replace("}\"","}")
					.replace("\"{","{")
					.replace("\"[", "[")
					.replace("]\"", "]");
    	
        JSONArray jsonArr = JSONArray.fromObject(jsonStr);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
        while(it.hasNext()){
            JSONObject json2 = it.next();
            list.add(jsonToMap(json2.toString()));
        }
        return list;
    }
    
    @SuppressWarnings("unchecked")
	public static List<Map<String, Object>> logisiticsParseJSON2List(Object jsonStr){
           JSONArray jsonArr = JSONArray.fromObject(jsonStr);
           List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
   		Iterator<JSONObject> it = jsonArr.iterator();
           while(it.hasNext()){
               JSONObject json2 = it.next();
               list.add(logisiticsJsonToMap(json2.toString()));
           }
           return list;
       }
    
    /** 
	    * 将json格式的字符串解析成Map对象 <li> 
	    * json格式：{"name":"admin","retries":"3fff","testname" 
	    * :"ddd","testretries":"fffffffff"} 
	    */  
	public static Map<String, Object> logisiticsJsonToMap(Object object)  
	   {  
	       Map<String, Object> data = new HashMap<String, Object>();  
	       // 将json字符串转换成jsonObject  
	       JSONObject jsonObject = JSONObject.fromObject(object);  
	       Iterator<?> it = jsonObject.keys();  
	       // 遍历jsonObject数据，添加到Map对象  
	       while (it.hasNext())  
	       {  
	           String key = String.valueOf(it.next());  
	           Object value =jsonObject.get(key);
	           if(value!=null){
	        	   value = value.toString().replaceAll("\"", "");
	           }
	           data.put(key, value);  
	       }  
	       return data;  
	   }  
	
	
	 /**
     * 
    *    返回 list 对象数组  字符串JSON要添加[] 
    * <br>详细说明
    * @param jsonStr json字符串
    * @return
    * @return List<Object> list
    * @throws
     */
	public static Object jsonToObjectList(String strJson, @SuppressWarnings("rawtypes")Class beanClass) {   
		return JSONArray.toCollection(JSONArray.fromObject(strJson), beanClass); 
	}  
	
}
