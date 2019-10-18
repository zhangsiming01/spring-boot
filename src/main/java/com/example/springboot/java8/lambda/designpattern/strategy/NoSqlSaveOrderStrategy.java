package com.example.springboot.java8.lambda.designpattern.strategy;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-23 10:03
 * @Description: NoSql 策略实现
 */
public class NoSqlSaveOrderStrategy implements OrderService {
    @Override
    public void saveOrder(String str) {
        System.out.println("order:"+str+"save to noSql");
    }
}
