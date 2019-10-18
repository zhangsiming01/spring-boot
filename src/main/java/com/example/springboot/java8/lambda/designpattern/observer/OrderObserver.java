package com.example.springboot.java8.lambda.designpattern.observer;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 10:59
 * @Description: 观察者第二个实现类
 */
public class OrderObserver implements Observer {
    @Override
    public void notify(String str) {
        System.out.println("订单" + str + "状态更新为【已支付】");
    }
}
