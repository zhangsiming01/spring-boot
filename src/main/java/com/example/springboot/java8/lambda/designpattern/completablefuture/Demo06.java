package com.example.springboot.java8.lambda.designpattern.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-29 11:00
 * @Description: 合并两个完全不相干的任务
 * 上一节，讲了thenCompose，接下来有另外一个方法，即thenCompine的使用。
 * 这个方法和thenCompose的区别即：我们需要把两个不互相依赖的结果同时计算出来进行计算，
 * 也就是我不希望第一个任务完成以后再来开始第二个任务的时候就可以使用这个方法。
 * 那么现在有个需求，就是我在计算出来商店的价格的时候，同时查找当时的汇率是多少，然后在最后使用这两个数字进行相乘。
 */
public class Demo06 {

    /***
     * 模拟查询价格
     * @return
     */
    public static Double getPrice() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Math.random() * 10;
    }

    /***
     *  模拟查询汇率
     * @return
     */
    public static Double getRate() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Math.random();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //第一个任务并获取返回值
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> getPrice())
                //第二个任务，获取返回值以后与第一个返回值进行计算
                .thenCombine(CompletableFuture.supplyAsync(() -> getRate()), (price, rate) -> price * rate);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("done in " + (System.currentTimeMillis() - start) + "ms");
    }
}
