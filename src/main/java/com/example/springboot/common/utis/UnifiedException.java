package com.example.springboot.common.utis;
/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月26日 上午8:40:06 
* 类说明 统一异常处理公共类
 */

import com.example.springboot.common.enumUtis.ErrorCodeConstants;

public class UnifiedException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	/**
	 * 错误编码
	 */
	private ErrorCodeConstants errorCodeConstants;

    /**
     * 构造一个基本异常.私有化默认构造方法，确保errorCode不为null
     *
     * @param message
     *            信息描述
     */
	private UnifiedException(String message) {
		super(message);
	}
	/**
     * 构造一个基本异常.
     *
     * @param errorCode
     *            错误编码
     */
	public UnifiedException(ErrorCodeConstants errorCodeConstants) {
		this(errorCodeConstants,"系统异常:"+errorCodeConstants.getErrorMessage());
	}
	/**
     * 构造一个基本异常.
     *
     * @param errorCode
     *            错误编码
     * @param message
     *            信息描述
     */
	public UnifiedException(ErrorCodeConstants errorCodeConstants,String message) {
		super(message);
		this.setErrorCodeConstants(errorCodeConstants);
	}
	
	/**
     * 构造一个基本异常.
     *
     * @param errorCode
     *            错误编码
     * @param message
     *            信息描述
     */
	public UnifiedException(ErrorCodeConstants errorCodeConstants,String message,Throwable cause) {
		super(message);
		this.setErrorCodeConstants(errorCodeConstants);
	}
	
	/**
	 *  获取异常提示
	 * @return ErrorCodeConstants
	 */
	public ErrorCodeConstants getErrorCodeConstants() {
		return errorCodeConstants;
	}
	
	/*
	 * 设置异常提示
	 */
	public void setErrorCodeConstants(ErrorCodeConstants errorCodeConstants) {
		this.errorCodeConstants = errorCodeConstants;
	}
	
	
}
