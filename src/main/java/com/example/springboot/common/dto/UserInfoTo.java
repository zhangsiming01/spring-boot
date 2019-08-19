package com.example.springboot.common.dto;

import com.example.springboot.common.Page;


/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 下午1:30:08 
* 类说明:获取用户信息接收实体
 */
public class UserInfoTo extends Page   {
	  private Integer id;

	    /**
	     * 账号
	     */
	    private String userCode;

	    /**
	     * 密码
	     */
	    private String userPassword;


	    /**
	     * @return id
	     */
	    public Integer getId() {
	        return id;
	    }

	    /**
	     * @param id
	     */
	    public void setId(Integer id) {
	        this.id = id;
	    }

	    /**
	     * 获取账号
	     *
	     * @return user_code - 账号
	     */
	    public String getUserCode() {
	        return userCode;
	    }

	    /**
	     * 设置账号
	     *
	     * @param userCode 账号
	     */
	    public void setUserCode(String userCode) {
	        this.userCode = userCode;
	    }

	    /**
	     * 获取密码
	     *
	     * @return user_password - 密码
	     */
	    public String getUserPassword() {
	        return userPassword;
	    }

	    /**
	     * 设置密码
	     *
	     * @param userPassword 密码
	     */
	    public void setUserPassword(String userPassword) {
	        this.userPassword = userPassword;
	    }
}
