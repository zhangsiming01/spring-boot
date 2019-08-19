package com.example.springboot.common.dto;
/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 上午10:48:02 
* 类说明 ：用于判断时间存在交集包含的实体
 */

import java.util.Date;

public class DateTo {
	private Date startTime;
	
	private Date endTime;
	/**
	 *  获取开始时间 
	 * @return startTime -开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 *  设置开始时间
	 * @param startTime 开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 *  获取结束时间
	 * @return endTime -结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 *  设置结束时间
	 * @param endTime 结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
