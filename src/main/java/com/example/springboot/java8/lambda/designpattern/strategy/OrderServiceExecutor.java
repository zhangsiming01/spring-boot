package com.example.springboot.java8.lambda.designpattern.strategy;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-23 10:10
 * @Description: 使用策略的辅助容器
 */
public class OrderServiceExecutor {
    private final OrderService service;

    public OrderServiceExecutor(OrderService service) {
        this.service = service;
    }


    public void save(String str) {
        this.service.saveOrder(str);
    }
}
