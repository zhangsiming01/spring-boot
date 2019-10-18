package com.example.springboot.java8.lambda.designpattern.templatemethod;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 10:13
 * @Description: 抽象模板类
 */
public abstract class AbstractPushTemplate {
    /***
     *  发送
     * @param customerId
     * @param shopName
     */
    public void push(int customerId, String shopName) {
        System.out.println("准备推送...");
        execute(customerId, shopName);
        System.out.println("推送完成\n");
    }

    abstract protected void execute(int customerId, String shopName);
}
