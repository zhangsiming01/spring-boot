package com.example.springboot.java8.lambda.designpattern.completablefuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-27 15:25
 * @Description: 实现异步API
 */
public class Demo02 {
    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");
        /**
         * java中System.nanoTime()返回的是纳秒，nanoTime而返回的可能是任意时间，甚至可能是负数……
         * java中System.currentTimeMillis()返回的毫秒，这个毫秒其实就是自1970年1月1日0时起的毫秒数.
         * 两个方法都不能保证完全精确,精确程度依赖具体的环境.
         */
        long startTime = System.nanoTime();
        Future<Double> shopPriceAsync = shop.getPriceAsync("best product");
        long invocationTime = ((System.nanoTime() - startTime) / 1_000_000);
        System.out.println("Invocation returned after" + invocationTime + "ms");

        try {
            //模拟做其他的事情耗时
            Thread.sleep(1000L);
            //获取结果
            double price = shopPriceAsync.get(1, TimeUnit.SECONDS);
            System.out.println("Price is " + price);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        long retrievalTime = (System.nanoTime() - startTime) / 1_000_000;
        System.out.println("Price returned after" + retrievalTime + "ms");
    }
}
