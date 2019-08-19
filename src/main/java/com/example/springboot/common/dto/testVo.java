package com.example.springboot.common.dto;

import java.util.Date;

/**
 * @Author: zhagnsiming
 * @CreateDate: 2018-10-22 16:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2018-10-22 16:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *  导出测试实体
 */
public class testVo {

    private  int id;

    private  String name;

    private  String password;

    private  double unitPrice;

    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
