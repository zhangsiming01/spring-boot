package com.example.springboot.java8.lambda.designpattern.observer;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 11:01
 * @Description:
 */
public class StockObserver implements Observer {
    @Override
    public void notify(String str) {
        System.out.println("订单" + str + "已通知库房发货！！");
    }
}
