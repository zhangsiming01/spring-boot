package com.example.springboot.java8.lambda.designpattern.templatemethod;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 10:21
 * @Description:
 */
public class TemplateTest {
    public static void main(String[] args) {
        //Java8 之前
        System.out.println("java8之前");
        AbstractPushTemplate template1 = new PushCouponTemplate();
        template1.push(1, "小明");

        AbstractPushTemplate template2 = new PushScoreTemplate();
        template2.push(2, "小刚");

        //Java8之后
        System.out.println("java8之后");
        new PushTemplateLambda().push(3, "小红", (Object[] obj) -> {
            System.out.println("会员：" + obj[0] + ",你好," + obj[1] + "送您一张优惠券");
        });
        new PushTemplateLambda().push(4, "小王", (Object[] obj) -> {
            System.out.println("会员：" + obj[0] + ",你好," + obj[1] + "送10个积分");
        });
    }
}
