package com.example.springboot.java8.lambda.designpattern.completablefuture;

/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-29 10:19
 * @Description: 折扣类 包含折扣方式枚举
 */
public class Discount {

    /**
     * 返回计算折扣后的价格
     *
     * @param price
     * @param discountCode
     * @return
     */
    public static String apply(double price, Code discountCode) {
        delay();
        return String.format(String.valueOf(price * (100 - discountCode.per) / 100));
    }


    /***
     * 模拟请求折扣的延迟
     */
    private static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 应用一个quote计算的类
     *
     * @param quote
     * @return
     */
    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + "price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    public enum Code {
        NONE(0),
        SILVER(5),
        GOLD(10),
        PLATINUM(15),
        DIAMOND(20);
        /**
         * 折扣百分比
         */
        private final int per;

        Code(int per) {
            this.per = per;
        }
    }
}
