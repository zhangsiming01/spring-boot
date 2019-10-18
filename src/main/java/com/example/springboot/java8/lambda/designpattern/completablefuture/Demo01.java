package com.example.springboot.java8.lambda.designpattern.completablefuture;

import java.util.concurrent.*;

/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-27 14:56
 * @Description: Future 执行一个耗时操作
 * Future接口  Future是jdk5的时候被引入的，目的是为了把耗时的操作解放出来，可以同时使用多核的优势进行并行处理。
 * 比如，我有一个页面，需要从多方获取数据比如说从Twitter和Facebook获取数据，然后一起渲染的页面上。
 * 这时候如果等待Twitter的数据获取完在获取FB的数据，就显得比较慢了，这时候可以通过Future来让这两个任务并行处理。
 * 1. Future的局限性
 * 等待Future集合所有都完成
 * 第二个线程依赖第一个线程的时候
 * 仅等待Future集合中最快完成的任务完成
 * 通过手工方式完成Future的运行
 * 当Future完成的时候通知下一个任务，而不只是简单的阻塞等待操作结果
 */
public class Demo01 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Double> future = executorService.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                Thread.sleep(1000L);
                return Math.random();
            }
        });
        try {
            Thread.sleep(1000L);
            //获取线程执行的结果，传递的是等待线程的时间，单位是1秒/ */
            future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("共耗时：" + (System.currentTimeMillis() - start) + "ms");
    }

}
