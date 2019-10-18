package com.example.springboot.java8.lambda.designpattern.templatemethod;

/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-24 10:17
 * @Description: 优惠券的具体模板方法
 */
public class PushCouponTemplate extends AbstractPushTemplate {
    @Override
    protected void execute(int customerId, String shopName) {
        System.out.println("会员：" + customerId + "，你好，" + shopName + "送您一张优惠券");
    }
}
