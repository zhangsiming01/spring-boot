package com.example.springboot.java8.lambda.designpattern.completablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static com.sun.javafx.runtime.async.BackgroundExecutor.getExecutor;

/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-29 11:10
 * @Description: Completion事件
 * 一组CompletableFuture完成以后执行的任务即使Completion事件，当对所有商店进行查询的时候，
 * 可能有些快一点，有些慢一点，但是我们会去等待比较慢的任务处理完成再来进行接下来的逻辑，这时候就可以使用future.thenAccept方法来完成这个任务。
 * 修改shop获取价格的延迟是随机性的。
 */
public class Demo07 {
    List<Shop1> shop1s;

    /**
     * 模拟网络延迟
     */
    public static void delay() {
        int delay = new Random().nextInt(2000) + 500;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Stream<CompletableFuture<String>> findPricesStream(String prodName) {
        return shop1s.stream()
                // 以异步的形式取得shop原始价格
                .map(shop1 -> CompletableFuture.supplyAsync(() -> shop1.getPrice1(prodName), getExecutor()))
                // Quote对象存在的时候，对其进行转换
                .map(future -> future.thenApply(Quote::parse))
                // 使用另一个异步任务构造申请折扣
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), getExecutor())));
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Demo07 demo07 = new Demo07();
        demo07.shop1s = Arrays.asList(
                new Shop1("shop1"),
                new Shop1("shop2"),
                new Shop1("shop3"),
                new Shop1("shop4"),
                new Shop1("shop5"));
        // 均全部返回CompletableFuture<Void>
        Stream<CompletableFuture<Void>> myIphoneThread
                = demo07.findPricesStream("myIphone").map(f -> f.thenAccept(priceStr -> {
            // 对每个价格进行输出打印
            System.out.println(priceStr + "(done in " + (System.currentTimeMillis() - start) + " ms)");
        }));

        /** 获取数组 */
        CompletableFuture[] completableFutures = myIphoneThread.toArray(size -> new CompletableFuture[size]);

        /** 等待所有线程完成 */
        CompletableFuture.allOf(completableFutures).join();

        System.out.println("all done in " + (System.currentTimeMillis() - start) + "ms");

    }
}
