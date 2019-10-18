package com.example.springboot.java8.lambda.designpattern.observer;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 11:02
 * @Description: 观察者测试
 */
public class ObserverTest {

    public static void main(String[] args) {
        test1();

        test2();
    }

    /**
     * java8之前
     */
    private static void test1() {
        System.out.println("java8之前");
        Subject subject = new SubjectImpl();
        subject.registerObserver(new OrderObserver());
        subject.registerObserver(new StockObserver());
        subject.notifyAllObserver("001");
    }

    /**
     * Java8 之后
     */
    private static void test2() {
        System.out.println("java8之后");
        NewSubject subject = new NewSubject() {
        };
        subject.registerObserver((String str) -> System.out.println("订单" + str + "状态更新为【已支付】"));
        subject.registerObserver((String str) -> System.out.println("订单" + str + "已通知库房发货！！"));
        subject.notifyAllObserver("002");

    }

}
