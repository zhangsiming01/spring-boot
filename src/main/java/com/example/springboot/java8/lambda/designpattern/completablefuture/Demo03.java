package com.example.springboot.java8.lambda.designpattern.completablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-27 15:38
 * @Description: https://www.jianshu.com/p/f9062567627f
 */
public class Demo03 {
    /**
     * 多家商店
     */
    List<Shop> shops;

    public static void main(String[] args) {
        Demo03 demo03 = new Demo03();
        demo03.shops = Arrays.asList(
                new Shop("Shop01"),
                new Shop("Shop02"),
                new Shop("Shop03"),
                new Shop("Shop04")
        );
        long start = System.currentTimeMillis();
        List<String> iPhone7 = demo03.findPrices1("iPhone7");
        System.out.println("Done in " + (System.currentTimeMillis() - start) + "ms");
    }

    /***
     * 顺序流查询
     * @param prodName
     * @return
     */
    private List<String> findPrices1(String prodName) {
        return shops.stream().map(shop -> String.format("%s price is %.2f", shop.getShopName(), shop.getPrice(prodName)))
                .collect(Collectors.toList());
    }

    /**
     *  并行流查询
     * @param prodName
     * @return
     */
    private List<String> findPrices2(String prodName) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getShopName(), shop.getPrice(prodName)))
                .collect(Collectors.toList());
    }

    /***
     * CompletableFuture方式异步请求
     * 结果并不像第二种方式中的那样如意，这时候我们可以引出下一个话题，
     * 对CompletableFuture的优化，CompletableFuture和并行流的对比，优势就在于CompletableFuture允许我们对执行器进行优化。
     * @param prodName
     * @return
     */
    private List<String> findPrices3(String prodName) {
        /** CompletableFuture方式异步请求 */
        List<CompletableFuture<String>> completableFutureList = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getShopName(), shop.getPrice(prodName))))
                .collect(Collectors.toList());

        return completableFutureList.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    /**
     * 获取一个优化的执行器
     * @return
     */
    private final Executor getExecutor(){
        /** 在设置线程数的时候，取一个上限的值，即如果商城超过100个的时候我们永远都只要100个线程数 */
        return Executors.newFixedThreadPool(Math.min(this.shops.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);// 设置守护线程，Java无法停止正在运行的线程
                return t;
            }
        });
    }

    /**
     * 4. 定制Excutor执行器
     * 在CompletableFuture.supplyAsync这个工厂方法中，我们可以传递我们自定义的Executor给线程。执行器的定义就需要我们斟酌了，在《Java并发编程实战》中有一道公式：N(threads)=Ncpu * Ucpu * (1+W/C)
     * 其中，Ncpu是cpu的个数，Ucpu是我们期望的CPU的利用率，而W/C是等待时间与计算时间的比率。我们就可以根据这个公式计算出我们所需要的线程数量了，但是线程数量不宜过多，也不宜过少
     * @param prodName
     * @return
     */
    public List<String> findPrices4(String prodName){
        /** CompletableFuture方式异步请求 */
        List<CompletableFuture<String>> completableFutureList = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getShopName(), shop.getPrice(prodName)),
                        getExecutor()))// 将我们自己定制的Executor传递给线程
                .collect(Collectors.toList());

        return completableFutureList.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
