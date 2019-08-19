package com.example.springboot.common.cache;

/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 下午2:44:03 
* 类说明 消息返回
 */
public class Constant {
	
	public final static String XML_TEMPLATE = "<request><addressReqList><addressReq><address>%s</address></addressReq></addressReqList></request>";
	/**
	 * 没有数据
	 */
	public final static String NOT_FOUND_DATA = "NOT_FOUND_DATA";
	/**
	 * 数据格式错误
	 */
	public final static String DATA_FORMAT_ERROR = "DATA_FORMAT_ERROR";
	/**
	 * 数据错误
	 */
	public final static String DATA_ERROR = "DATA_ERROR";

	/**
	 * 成功
	 */
	public final static String SUCCESS = "SUCCESS";
	/**
	 * 重复拉取
	 */
	public final static String REPEAT = "REPEAT";

	/**
	 * 系统异常
	 */
	public final static String SYSTEM_ERROR = "SYSTEM_ERROR";
	/**
	 * 请求数据不能为空
	 */
	public final static String REQUEST_DATA_NOT_FOUND = "REQUEST_DATA_NOT_FOUND";
	/**
	 * 请求超时
	 */
	public final static String TIME_OUT = "TIME_OUT";
	/**
	 * 登录信息已过期
	 */
	public final static String LOGIN_TIME_OUT="LOGIN_TIME_OUT";


}
