package com.example.springboot.lock;

/**
 * @author: zhangsiming
 * @Date: 2019/10/28 13:16
 * @Description: synchronizedLock 示例
 * 实例锁
 */
public class SynchronizedLock {
    public synchronized void demo(String param) {
        synchronized (this) {
            //实例锁
            System.out.println("实例锁");
        }
    }

    public static void main(String[] args) {
        SynchronizedLock synchronizedLock = new SynchronizedLock();
        SynchronizedLock synchronizedLock1 = new SynchronizedLock();
        //下面两个代码排队执行
        new Thread(() -> synchronizedLock.demo(Thread.currentThread().getId() + "")).start();
        new Thread(() -> synchronizedLock1.demo(Thread.currentThread().getId() + "")).start();
    }
}
