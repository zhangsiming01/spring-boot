package com.example.springboot.common.enumUtis;

import com.example.springboot.common.cache.Result;

/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 下午5:21:06 
* 类说明 错误信息枚举
 */
public enum ErrorCodeConstants {
	
	/**
	 * 错误码在工程中出现次数必须全局唯一
	 */
	
	/**
	 * 设置错误信息begin
	 */
	/**
	 * 用户模板错误信息
	 */
	
	USER_0001("USER_0001","该账号已存在无法新增编辑"),
	
	USER_0002("USER_0002","导出数据失败"),
	
	USER_0003("USER_0003", "表格解析失败，请联系系统管理员"),
	
	/**
	 * 下载文件
	 */
	DOWNLOAD_0001("DOWNLOAD_0001","下载文件不存在！！！"),


    /***
     * pdf
     */
    PDF("PDF","the size of file  out of range"),

    /***
     * redis 分布式锁
     */
    REDIS_LOCK("REDIS_LOCK","lock key can't be null..."),
    REDIS_LOCK_01("REDIS_LOCK_01","请勿重复请求"),
    REDIS_LOCK_02("REDIS_LOCK_02","系统异常 "),
    ;



    /**
     * 错误code
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    ErrorCodeConstants(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;

    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(Object... args) {
        return String.format(errorMessage, args);
    }

    public void generateError(Result<?> result) {
        result.setStatusCode(getErrorCode());
        result.setMessage(getErrorMessage());
    }
}
