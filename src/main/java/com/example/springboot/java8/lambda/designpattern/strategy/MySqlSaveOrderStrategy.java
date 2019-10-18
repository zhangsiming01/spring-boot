package com.example.springboot.java8.lambda.designpattern.strategy;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-23 09:57
 * @Description: 策略模式，MySQL策略实现类
 */
public class MySqlSaveOrderStrategy implements OrderService {
    @Override
    public void saveOrder(String str) {
        System.out.println("order:"+str+"save to mysql");
    }
}
