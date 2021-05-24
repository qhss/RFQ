package com.rfq.utils;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

public class HttpClientUtil {
	
	/**
	 *   发送文件到指定 url
	 * @param url
	 * @param file
	 * @param charset 返回结果的字符串字符集类型
	 * @return
	 */
	public static String doPostFile(String url, MultipartFile file, String returnCharset) {

		HttpPost httppost = new HttpPost(url);
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			InputStreamBody inputStreamBody = new InputStreamBody(file.getInputStream(), file.getOriginalFilename());
			
			MultipartEntityBuilder builder = MultipartEntityBuilder.create() 
					.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.addPart("file", inputStreamBody); 
			
			//设置请求的数据
			HttpEntity httpEntity = builder.build();
			httppost.setEntity(httpEntity);
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, Charset.forName(returnCharset));
			}
			return null;
		} catch (ClientProtocolException e) {
			System.err.println(e);
			return null;
		} catch (IOException e) {
			System.err.println(e);
			return null;
		} finally {
			if (httppost != null) {
				httppost.abort();
			}
		}
	}
}
