package com.example.springboot.common.cache;

import java.io.Serializable;

/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 下午2:42:39 
* 类说明 返回数据载体
 */
public class Message<T> implements Serializable {

	private static final long serialVersionUID = 841172369108781628L;
	
	private boolean status = false;
	private String statusCode;
	private String message;
	private T result = null;
	
	public Message() {
		super();
	}

	public Message(String statusCode, String message ,T result) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.result = result;
	}
	
	public Message(boolean status, String statusCode, String message,T result) {
		super();
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
		this.result = result;
	}

	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	
}
