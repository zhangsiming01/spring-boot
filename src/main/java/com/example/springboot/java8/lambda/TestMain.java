package com.example.springboot.java8.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-08-30 14:13
 **/
public class TestMain {
    public static void main(String[] args) throws IOException {
        String str = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(str);
        test();
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
        test9();
        test10();
    }

    private static void test10() {
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree =
                someNumbers.stream()
                        .map(x -> x * x)
                        .filter(x -> x % 3 == 0)
                        .findFirst(); // 9
        System.out.println(firstSquareDivisibleByThree);
    }

    /***
     * anyMatch表示，判断的条件里，任意一个元素成功，返回true
     allMatch表示，判断条件里的元素，所有的都是，返回true
     noneMatch跟allMatch相反，判断条件里的元素，所有的都不是，返回true
     */
    private static void test9() {
        List<String> strs = Arrays.asList("a", "a", "a", "a", "b");
        boolean aa = strs.stream().anyMatch(str -> str.equals("a"));
        boolean bb = strs.stream().allMatch(str -> str.equals("a"));
        boolean cc = strs.stream().noneMatch(str -> str.equals("a"));
        long count = strs.stream().filter(str -> str.equals("a")).count();
        // TRUE
        System.out.println(aa);
        // FALSE
        System.out.println(bb);
        // FALSE
        System.out.println(cc);
        // 4
        System.out.println(count);
    }

    private static void test8() {
        List<String> str1s = Arrays.asList("a", "b", "c", "d", "e");
        List<String> str2s = Arrays.asList("a", "r", "e", "j", "l");
        //交集（包含）
        List<String> intersection = str1s.stream().filter(str -> str2s.contains(str)).collect(Collectors.toList());
        System.out.println("交集" + intersection);

        //差集（str1s-str2s）
        List<String> reduce1 = str1s.stream().filter(str -> !str2s.contains(str)).collect(Collectors.toList());
        System.out.println("差集" + reduce1);

        //差集（str2s-str1s）
        List<String> reduce2 = str2s.stream().filter(str -> !str1s.contains(str)).collect(Collectors.toList());
        System.out.println("差集" + reduce2);

        //并集
        List<String> all1 = str1s.parallelStream().collect(Collectors.toList());
        List<String> all2 = str2s.parallelStream().collect(Collectors.toList());
        all1.addAll(all2);
        System.out.println("并集" + all1);
        //去重并集
        List<String> allDistinct = all1.stream().distinct().collect(Collectors.toList());
        System.out.println("去重并集" + allDistinct);

    }


    /***
     * BiFunction的一个特殊例子，接收两个参数，产生一个结果，
     * 只是它的三个参数都是同一个数据类型，这个函数式接口就是BinaryOperator
     */
    private static void test5() {
        TestMain.testBinaryOperator(1, 2, (num1, num2) -> num1 + num2);
        TestMain.testBinaryOperator(4, 2, (num1, num2) -> num1 - num2);
        TestMain.testBinaryOperator(3, 2, (num1, num2) -> num1 * num2);
        TestMain.testBinaryOperator(8, 2, (num1, num2) -> num1 / num2);
        TestMain.testMinBy("hello", "wonders", (str1, str2) -> str1.length() - str2.length());
        //方法引用
        TestMain.testMinBy("hello", "wonders", Comparator.comparingInt(String::length));
        TestMain.testMinBy("hello", "wonders", (str1, str2) -> str1.charAt(0) - str2.charAt(0));
        //方法引用
        TestMain.testMinBy("hello", "wonders", Comparator.comparingInt(str -> str.charAt(0)));
        TestMain.testMaxBy("hello", "wonders", (str1, str2) -> str1.length() - str2.length());
        //方法引用
        TestMain.testMaxBy("hello", "wonders", Comparator.comparingInt(String::length));
        TestMain.testMaxBy("hello", "wonders", (str1, str2) -> str1.charAt(0) - str2.charAt(0));
        //方法引用
        TestMain.testMaxBy("hello", "wonders", Comparator.comparingInt(str -> str.charAt(0)));
    }

    private static void testBinaryOperator(Integer num1, Integer num2, BinaryOperator<Integer> result) {
        System.out.println(result.apply(num1, num2));
    }

    /***
     * 返回两者里面较小的一个
     * @param str1
     * @param str2
     * @param comparator
     */
    private static void testMinBy(String str1, String str2, Comparator<String> comparator) {
        System.out.println(BinaryOperator.minBy(comparator).apply(str1, str2));
    }

    /***
     *  返回两者里面较大的一个
     * @param str1
     * @param str2
     * @param comparator
     */
    private static void testMaxBy(String str1, String str2, Comparator<String> comparator) {
        System.out.println(BinaryOperator.maxBy(comparator).apply(str1, str2));
    }

    /***
     * JAVA8之函数式编程Supplier接口和Consumer接口
     * Supplier接口 该接口就一个抽象方法get方法(这是一个供应商,提供者.就如一个工厂一样).
     * Consumer接口 (这是一个消费者)
     * 1.    accept方法  该函数式接口的唯一的抽象方法,接收一个参数,没有返回值.
     *  2.    andThen方法
     *  在执行完调用者方法后再执行传入参数的方法.
     *  可以看出先将10传入consumer方法执行,然后再将10传入consumer2方法执行.
     */
    private static void test4() {
        Consumer<Integer> consumer = (x) -> {
            int num = x * 2;
            System.out.println(num);
        };

        Consumer<Integer> consumer1 = (x) -> {
            int num = x * 3;
            System.out.println(num);
        };
        consumer.andThen(consumer1).accept(10);
    }

    /****
     * java8 Predicate接口的使用
     */
    private static void test3() {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        List<Integer> list = new ArrayList<>();
        for (int i : numbers) {
            list.add(i);
        }
        Predicate<Integer> p1 = i -> i > 5;
        Predicate<Integer> p2 = i -> i < 20;
        Predicate<Integer> p3 = i -> i % 2 == 0;
        List test = list.stream().filter(p1.and(p2).and(p3)).collect(Collectors.toList());
        System.out.println(test.toString());
    }


    /***
     * Java8 Function接口的使用
     */
    private static void test2() {
        Function<Integer, Integer> A = i -> i + 1;
        Function<Integer, Integer> B = i -> i * i;
        System.out.println("F1:" + B.apply(A.apply(5)));
        System.out.println("F1:" + B.compose(A).apply(5));
        System.out.println("F2:" + A.apply(B.apply(5)));
        System.out.println("F2:" + B.andThen(A).apply(5));
    }

    private static void test1() {
        List<String> strs = Arrays.asList("a", "b", "c", "d", "e");
        strs.sort(String::compareToIgnoreCase);
    }

    private static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("D:\\kk.txt"))) {
            return p.process(br);
        }
    }


    interface BufferedReaderProcessor {
        /**
         * 处理流
         *
         * @param b
         * @return
         * @throws IOException
         */
        String process(BufferedReader b) throws IOException;
    }


    private static void test() {
        //两个集合赋值
        List<Test1> test1List = new ArrayList<>();
        List<Test2> test2List = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Test1 test1 = new Test1();
            test1.setAge(i);
            test1.setName("name" + i);
            test1List.add(test1);
        }

        test1List.parallelStream().forEach(test -> {
            Test2 test2 = new Test2();
            test2 = getData(test2, test);
            test2List.add(test2);
        });
        test2List.stream().forEach(test2 -> System.out.println("age" + test2.getAge() +
                "name" + test2.getName()));
    }

    private static Test2 getData(Test2 test2, Test1 test) {
        test2.setAge(test.getAge());
        test2.setName(test.getName());
        return test2;
    }

    private static boolean getData1(Test1 test1) {
        if (test1.getAge() > 5) {
            return true;
        }
        return false;
    }

    /***
     * 引入流
     */
    private static void test6() {
        List<Test1> test1List = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Test1 test1 = new Test1();
            test1.setAge(i);
            test1.setName("name1" + i);
            test1List.add(test1);
        }
        //stream 遍历 filter 筛选 sorted 排序 map提取元素 collect 存入list
        List<String> list = test1List.parallelStream().filter(t -> t.getAge() > 5)
                .sorted(comparing(Test1::getName)).map(Test1::getName).limit(3).collect(Collectors.toList());
        System.out.println(list);
        long count = test1List.stream()
                .filter(t -> t.getAge() > 8)
                .distinct()
                .limit(3)
                .count();
        System.out.println(count);

        List<Test1> data = test1List.stream().filter(TestMain::getData1).collect(Collectors.toList());
        System.out.println(data);

        //查找满足条件第一个值
        Optional<Test1> dish = test1List.stream().filter(Test1::getData1).findAny();
        System.out.println("查找" + dish);

        //查找满足条件的值
        test1List.stream().filter(Test1::getData1).findAny().ifPresent(test1 -> System.out.println("查找1" + test1.getName()));

        test1List.stream().filter(t -> t.getAge() > 5).skip(0).forEach(System.out::println);

        List<Integer> data1 = test1List.stream().map(Test1::getName).map(String::length).collect(Collectors.toList());
        System.out.println(data1);

        List<String> arrayOfWords = Arrays.asList("Goodbye", "World");
        List<String> uniqueCharacters =
                arrayOfWords.stream()
                        .map(w -> w.split(""))
                        .flatMap(Arrays::stream)
                        .distinct()
                        .collect(Collectors.toList());
        System.out.println(uniqueCharacters);
    }

    private static void test7() {
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 1, 6, 8, 7, 9, 6);
        numbers.stream().filter(i -> i % 2 == 0).distinct().forEach(System.out::print);
    }
}
