package com.rfq.entity;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

public class ServiceResult<T> {
	
	private boolean success;
	
	private String errorMessage;
	
	private T data;
	
	public ServiceResult(T data) {
		this.setData(data);
		this.setErrorMessage("");
		this.setSuccess(true);
	}
	
	/**
	 * 创建服务端结果
	 * @param isError
	 * @param errorMessage
	 */
	public ServiceResult(boolean isError, String errorMessage) {
		if(isError) {
			//统一进行事务回滚
			this.setData(null);
			this.setErrorMessage(errorMessage);
			this.setSuccess(false);
		}
	}
	
	/**
	 * 创建服务端结果,并回滚事务
	 * @param isError
	 * @param transactionRollback
	 * @param errorMessage
	 */
	public ServiceResult(boolean isError,boolean transactionRollback, String errorMessage) {
		if(isError) {
			if(transactionRollback)
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			//统一进行事务回滚
			this.setData(null);
			this.setErrorMessage(errorMessage);
			this.setSuccess(false);
		}
	}
	
	public ServiceResult() {
		
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
