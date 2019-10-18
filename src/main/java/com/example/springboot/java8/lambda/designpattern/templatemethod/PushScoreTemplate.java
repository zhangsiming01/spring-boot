package com.example.springboot.java8.lambda.designpattern.templatemethod;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 10:19
 * @Description: 积分的具体模板
 */
public class PushScoreTemplate extends AbstractPushTemplate {
    @Override
    protected void execute(int customerId, String shopName) {
        System.out.println("会员：" + customerId + ",你好," + shopName + "送您10个积分");
    }
}
