package com.example.springboot.java8.lambda;

import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: spring-boot
 * @description: 并行流数据处理与性能
 * @author: zsm
 * @create: 2019-09-16 10:19
 **/
public class Test4Main {

    public static void main(String[] args) {
        test1();
        test4();
        test2();

    }

    /***
     *  使用日志调式
     */
    private static void test2() {
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5, 6);
        List<Integer> result = numbers.stream()
                .peek(x -> System.out.println("from stream:" + x))
                .map(x -> x + 17)
                .peek(x -> System.out.println("after map:" + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter:" + x))
                .limit(3)
                .peek(x -> System.out.println("after limit:" + x))
                .collect(Collectors.toList());
        System.out.println("日志打印：" + result);
    }


    private static void test4() {
        List<Test1> test1s = getData();
        test1s.sort(Comparator.comparingInt(Test1::getAge).reversed());
        System.out.println(test1s);
        System.out.println(test1s.stream().filter(t -> t.getAge() > 8).collect(Collectors.toList()));

    }

    public static List<Test1> getData() {
        List<Test1> test1s = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Test1 test1 = new Test1();
            test1.setName("a" + i);
            test1.setAge(i);
            test1s.add(test1);
        }
        return test1s;
    }


    /***
     * Lambda的延迟执行
     */
    private static void test1() {
        String msgA = "Hello ";
        String msgB = "world ";
        String msgC = "Java ";
        log(2, () -> {
            System.out.println("Lambda执行！");
            return msgA + msgB + msgC;
        });
    }

    /***
     * https://www.cnblogs.com/heliusKing/p/10989113.html
     * @param level
     * @param builder
     */
    private static void log(int level, MessageBuilder builder) {
        if (level == 1) {
            System.out.println(builder.buildMessage());
        }
    }

}
