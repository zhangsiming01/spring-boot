package com.example.springboot.java8.lambda.designpattern.completablefuture;

import java.util.Random;

/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-29 09:52
 * @Description: 对多个异步任务进行流水线操作
 * 我们假设现在需要实现一个需求，就是所有商店都允许使用一个折扣，然后我们异步获取所有商店的价格后，
 * 再通知另外一个异步线程，计算出价格打折扣以后的数据。这时候求出折扣的价格线程就依赖到了商店获取价格的线程了。
 * 假设需要修改商店价格和折扣返回的形式是：BestShop:123.26:GOLD。这时候我们需要对getPrice进行修改
 */
public class Shop1 {

    private String shopName;

    public Shop1(String shopName) {
        this.shopName = shopName;
    }

    public Shop1() {
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPrice1(String prodName) {
        double price = calculatePrice1(prodName);
        Discount.Code code = Discount.Code.values()[new Random().nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", shopName, price, code);
    }

    private double calculatePrice1(String prodName) {
        delay();
        return new Random().nextDouble() * prodName.charAt(0) + prodName.charAt(1);
    }

    /***
     * 模拟网络延迟
     */
    private static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
