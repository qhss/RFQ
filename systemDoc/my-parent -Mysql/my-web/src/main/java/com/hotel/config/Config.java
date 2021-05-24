package com.hotel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Config {
	public static String SUB_SYSTEM_DAODI="his";
	public static String SUB_SYSTEM_ONE="one";
	
	/**
	 * 图片网络路径: 例如:
	 */
	@Value("${app.img.host}")
	private String imgHttpHost;

	/**
	 * 图片物理路径
	 */
	@Value("${app.img.phypath}")
	private String imgPhyPath;

	/**
	 * 是否启用钉钉异常消息推送
	 */
	@Value("${app.dingtalk.enable}")
	private Boolean dingTalkEnable;
	/**
	 * 钉钉群token
	 */
	@Value("${app.dingtalk.token}")
	private String dingTalkToken;
	
	//钉钉运维通知群
	@Value("${app.dingtalk.operation.token}")
	private String dingTalkOperationToken;
	/**
	 * at的钉钉手机会员号
	 */
	@Value("${app.dingtalk.at.mobiles}")
	private String dingTalkAtMobiles;
	
	/**
	 * at的姓名
	 */
	@Value("${app.dingtalk.at.names}")
	private String dingTalkAtNames;
	

	/**
	 * 文件服务 - 上传地址
	 */
	@Value("${app.file.server.url}")
	private String fileServerUrl;
	
	public String getImgHttpHost() {
		return imgHttpHost;
	}

	public void setImgHttpHost(String imgIp) {
		this.imgHttpHost = imgIp;
	}

	public String getImgPhyPath() {
		return imgPhyPath;
	}

	public void setImgPhyPath(String imgPhyPath) {
		this.imgPhyPath = imgPhyPath;
	}

	

	/**
	 * @return the dingTalkToken
	 */
	public String getDingTalkToken() {
		return dingTalkToken;
	}

	/**
	 * @param dingTalkToken the dingTalkToken to set
	 */
	public void setDingTalkToken(String dingTalkToken) {
		this.dingTalkToken = dingTalkToken;
	}

	/**
	 * @return the dingTalkAtMobiles
	 */
	public String getDingTalkAtMobiles() {
		return dingTalkAtMobiles;
	}

	/**
	 * @param dingTalkAtMobiles the dingTalkAtMobiles to set
	 */
	public void setDingTalkAtMobiles(String dingTalkAtMobiles) {
		this.dingTalkAtMobiles = dingTalkAtMobiles;
	}

	/**
	 * @return the dingTalkAtNames
	 */
	public String getDingTalkAtNames() {
		return dingTalkAtNames;
	}

	/**
	 * @param dingTalkAtNames the dingTalkAtNames to set
	 */
	public void setDingTalkAtNames(String dingTalkAtNames) {
		this.dingTalkAtNames = dingTalkAtNames;
	}

	/**
	 * @return the dingTalkEnable
	 */
	public Boolean getDingTalkEnable() {
		return dingTalkEnable;
	}

	/**
	 * @param dingTalkEnable the dingTalkEnable to set
	 */
	public void setDingTalkEnable(Boolean dingTalkEnable) {
		this.dingTalkEnable = dingTalkEnable;
	}

	

	/**
	 * @return the dingTalkOperationToken
	 */
	public String getDingTalkOperationToken() {
		return dingTalkOperationToken;
	}

	/**
	 * @param dingTalkOperationToken the dingTalkOperationToken to set
	 */
	public void setDingTalkOperationToken(String dingTalkOperationToken) {
		this.dingTalkOperationToken = dingTalkOperationToken;
	}


	public String getFileServerUrl() {
		return fileServerUrl;
	}

	public void setFileServerUrl(String fileServerUrl) {
		this.fileServerUrl = fileServerUrl;
	}

	
	

}
