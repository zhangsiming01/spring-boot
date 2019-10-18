package com.example.springboot.java8.lambda.designpattern.responsibilitychain;


import java.util.function.Consumer;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 11:24
 * @Description: 责任链测试类
 */
public class ProcessorTest {

    public static void main(String[] args) {
        test1();
        test2();
    }

    /**
     * java8 之前
     */
    private static void test1() {
        System.out.println("Java8之前");
        Processor p1 = new ProcessorImpl(null);
        Processor p2 = new ProcessorImpl2(p1);
        p2.process("something happened");
    }

    /***
     * java8 之后
     * andThen天然就是getNextProcessor的另一种表达
     * 重要提示：什么时候该用lambda，什么时候不用，这是要看情况的，
     * 如果处理逻辑相对比较简单，可以用lamdba来重构，以便让代码更简洁易读，如果处理逻辑很复杂，应该还是用“类”。
     */
    private static void test2() {
        System.out.println("java8之后");
        Consumer<String> p1 = param -> System.out.println("processor 1 is processing" + param);
        Consumer<String> p2 = param -> System.out.println("processor 2 is processing" + param);
        p2.andThen(p1).accept("something happened");
    }
}
