package com.example.springboot.dal.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author zt
 */
@Table(name = "user_info")
public class UserInfo implements Serializable {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 账号
     */
    @Column(name = "user_code")
    private String userCode;

    /**
     * 密码
     */
    @Column(name = "user_password")
    private String userPassword;


    private static final long serialVersionUID = 1L;

    /**
     * 获取自增id
     *
     * @return id - 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增id
     *
     * @param id 自增id
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