package com.hotel.entity.sys;

public class Result {
	private int success;
	private Object data;
	private String errorCode;
	private String errorMessage;

	public Result() {
		super();
	}

	public Result(int success, Object data, String errorCode, String errorMessage) {
		super();
		this.success = success;
		this.data = data;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorcode) {
		this.errorCode = errorcode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/***
	 * 返回参数不完整错误
	 * 
	 * @return
	 */
	public void setMissingParameErrorResult() {
		setErrorResult("400001", "参数不完整");
	}

	/***
	 * 返回错误编号及信息
	 * 
	 * @param errorCode    错误代码
	 * @param errorMessage 错误描述
	 * @return
	 */
	public void setErrorResult(String errorCode, String errorMessage) {
		setSuccess(0);
		setErrorCode(errorCode);
		setErrorMessage(errorMessage);
		setData("");
	}

	/***
	 * 返回系统错误
	 * 
	 * @param e 异常错误对象
	 * @return
	 */
	public void setErrorResult(Exception e) {
		setSuccess(0);
		setErrorCode("400000");
		setErrorMessage(e.getMessage());
		setData("");
	}

	/**
	 * 返回成功获取数据
	 * 
	 * @param obj
	 * @return
	 */
	public void setRightResult(Object obj) {
		setSuccess(1);
		setErrorCode("");
		setErrorMessage("");
		setData(obj);
	}
}
