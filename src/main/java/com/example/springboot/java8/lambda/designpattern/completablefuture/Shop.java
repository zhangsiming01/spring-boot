package com.example.springboot.java8.lambda.designpattern.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-27 15:09
 * @Description: 商品类
 * 2. 实现异步API
 * 首先模拟在商店里面找到我们自己想要的产品的价格，在寻找的过程中让线程休息1秒模拟网络延迟，然后随机返回一个价格。
 * 第一步：
 * 写一个模拟延迟的方法delay()，模拟线程休息一秒钟
 * 第二步：
 * 写一个计算的方法，用于计算价格，这里我们使用随机数生成
 * 第三步：
 * 暴露getPrice(String prodName)用于外部调用。
 * 第四步：
 * 暴露异步获取线程的接口
 * Shop.java：
 */
public class Shop {
    private String shopName;

    public Shop(String shopName) {
        this.shopName = shopName;
    }

    public Shop() {
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /***
     *  获取产品价格
     * @param prodName
     * @return
     */
    public double getPrice(String prodName) {
        return calculatePrice(prodName);
    }

    /***
     * 计算产品价格
     * @param prodName
     * @return
     */
    private double calculatePrice(String prodName) {
        delay();
        return new Random().nextDouble() * prodName.charAt(0) + prodName.charAt(1);
    }

    /***
     * 模拟网络延迟
     */
    private void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * 异步请求产品价格方式一
     * @param prodName
     * @return
     */
    public Future<Double> getPriceAsync1(String prodName) {
        //1、使用第一种方发
        /**
         *
         */
        //创建CompletableFuture 在另一个 对象，它会包含计算的结果
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            //在另一个 对象，它会包含计算的结果 线程中以异步方式执行计算
            double price = calculatePrice(prodName);
            //需长时间计算的任务结束并得出结果时，设置Future的返回值
            future.complete(price);
        }).start();
        //无需等待还没结束的计算，直接返回Future对象
        return future;


    }

    /***
     *  异步请求产品价格2
     * @param prodName
     * @return
     */
    public Future<Double> getPriceAsync(String prodName) {
        //第二种方式工厂模式的方式
        return CompletableFuture.supplyAsync(() -> calculatePrice(prodName));
    }
}
