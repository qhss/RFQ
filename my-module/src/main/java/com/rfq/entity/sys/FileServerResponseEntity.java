package com.rfq.entity.sys;

import java.io.Serializable;

public class FileServerResponseEntity implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -4527519540012830360L;
//	[{"pathUrl":"c:\\uploadFiles\\doctor\\1539738814979.jpg",
//	"fwUrl":"http://www.syrjia.cn/uploadFiles/doctor/1539738814979.jpg"}]
	public static Integer CONST_Upload_Success = 1;
	public static Integer CONST_Upload_Fail = 0;
	/**
	 * 上传结果1 成功; 0 失败
	 */
	private Integer status;
	/**
	 * status = 0 时, 显示错误提示;
	 */
	private String msg;
	/**
	 * 加密Key
	 */
	private String encryptionKey;
	/**
	 * 物理路径
	 */
	private String pathUrl;
	/**
	 * 网络路径
	 */
	private String fwUrl;

	/**
	 * 下载地址
	 */
	private String downloadUrl;

	/**
	 * 缩略图（仅对图片文件有效）
	 */
	private String thumbnailUrl;

	public FileServerResponseEntity() {

	}

	public static FileServerResponseEntity error() {
		return FileServerResponseEntity.error(CONST_Upload_Fail, "");
	}

	public static FileServerResponseEntity error(Integer status, String msg) {
		FileServerResponseEntity resp = new FileServerResponseEntity();
		resp.setStatus(status);
		resp.setMsg(msg);
		return resp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPathUrl() {
		return pathUrl;
	}

	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}

	public String getFwUrl() {
		return fwUrl;
	}

	public void setFwUrl(String fwUrl) {
		this.fwUrl = fwUrl;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	@Override
	public String toString() {
		String resp = this.status + "|" + this.msg + "|" + this.pathUrl + "|" + this.fwUrl + "|" + this.thumbnailUrl + "|" + this.encryptionKey;
		return resp;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
}
