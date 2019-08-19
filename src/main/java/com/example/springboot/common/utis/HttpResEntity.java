package com.example.springboot.common.utis;

import java.io.Serializable;

/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 上午11:18:15 
* 类说明 返回格式工具类
 */
public class HttpResEntity implements Serializable {
	private static final long serialVersionUID = -5716617440635510066L;
	private Integer status;
	private String data;
	private String msg;

	/**
	 * 提示
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 状态为200成功
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 返回结果
	 * @return
	 */
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
