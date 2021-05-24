package com.hotel.utils;

import java.security.MessageDigest;

//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * md5加密
 */
public class Md5Encoder {
	
	
	
	/**
	 *MD5加密
	 * @param str 需要加密的字符串
	 * @return
	 */
	public static String getMd5(String str){
		try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(str.getBytes("utf-8"));
	        return toHex(bytes).toLowerCase();
	    }
	    catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	        
	}
	
	private static String toHex(byte[] bytes) {

	    final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
	    StringBuilder ret = new StringBuilder(bytes.length * 2);
	    for (int i=0; i<bytes.length; i++) {
	        ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
	        ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
	    }
	    return ret.toString();
	}
	
}
