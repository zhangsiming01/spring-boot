package com.example.springboot.java8.lambda.designpattern.completablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.sun.javafx.runtime.async.BackgroundExecutor.getExecutor;

/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-29 10:38
 * @Description: 对多个异步任务进行流水线操作
 * 重点放在findPrices(String prodName)这个方法上：
 *
 * 我们拿到所有的shop进行遍历，把shop流变成一个CompletableFuture<String>的流，这个六包含着所有商店获取的价格字符串（BestShop:123.26:GOLD）格式的线程。
 * 拿到字符串的时候，我们通过future.thenApply方法将这些字符串转换成quote对象（该对象包含字符串解析出来的信息）
 * future.thenCompose再把上一步拿到的已经转换成Quote对象的CompletableFuture流进行进一步的通知，即通知CPU可以异步执行计算折扣的代码了
 * 收集到所有的CompletableFuture流
 * CompletableFuture::join意为等待所有的Future线程（获取价格、计算折扣）执行完成的时候，收集到所有的数据进行返回。
 */
public class Demo05 {
    List<Shop1> shop1s;

    public static void main(String[] args) {
        Demo05 demo05 = new Demo05();
        demo05.shop1s = Arrays.asList(
                new Shop1("shop1"),
                new Shop1("shop2"),
                new Shop1("shop3"),
                new Shop1("shop4"),
                new Shop1("shop5"));
        long start = System.currentTimeMillis();
        List<String> iPhone7 = demo05.findPrices("iPhone7");
        System.out.println("Done in" + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     *  寻找打折扣后产品价格
     * @param prodName
     * @return
     */
    public List<String> findPrices(String prodName) {
        List<CompletableFuture<String>> completableFutures = shop1s.stream()
                // 以异步的形式取得shop原始价格
                .map(shop1 -> CompletableFuture.supplyAsync(() -> shop1.getPrice1(prodName), getExecutor()))
                // Quote对象存在的时候，对其进行转换
                .map(future -> future.thenApply(Quote::parse))
                // 使用另一个异步任务构造申请折扣
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), getExecutor())))
                .collect(Collectors.toList());
        return completableFutures.stream()
                //等待流中所有future 完毕并提取各自返回值
                .map(CompletableFuture::join).collect(Collectors.toList());
    }
}
