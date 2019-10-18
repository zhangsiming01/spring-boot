package com.example.springboot.java8.lambda;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-09-03 09:31
 **/
public class Test1Main {

    public static void main(String[] args) {
        test();
        test1();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
    }

    /**
     * 用分支/合并框架执行并行求和
     *
     * @param n
     * @return
     */
    private static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    private static void test8() {
        System.out.println(forkJoinSum(55));
    }

    /**
     * 测量流性能
     */
    private static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    private static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    private static void test7() {
        System.out.println("Parallel range sum done in:" +
                measureSumPerf(Test1Main::parallelRangedSum, 10_000_000) +
                " msecs");
    }

    /***
     * 将顺序流转换为并行流
     */
    private static void test6() {
        long a = Stream.iterate(1L, i -> i + 1).limit(5).parallel().reduce(0L, Long::sum);
        System.out.println(a);

    }

    private static void test5() {
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).stream();
        List<Integer> numbers = stream.reduce(new ArrayList<Integer>(),
                (List<Integer> l, Integer i) -> {
                    l.add(i);
                    return l;
                }, (List<Integer> l1, List<Integer> l2) -> {
                    l1.addAll(l2);
                    return l1;
                });
        System.out.println(numbers);
    }

    public enum CaloricLevel {DIET, NORMAL, FAT}

    private static void test4() {
        List<Test1> test1List = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Test1 test1 = new Test1();
            test1.setAge(i);
            test1.setName("name2" + i);
            test1List.add(test1);
        }
        //返回 int
        Comparator<Test1> dishCaloriesComparator = Comparator.comparingInt(Test1::getAge);
        //获取最大
        Optional<Test1> mostCalorie = test1List.stream().collect(maxBy(dishCaloriesComparator));
        Optional<Test1> mostCalorie1 = test1List.stream().collect(minBy(dishCaloriesComparator));
        //汇总
        int totalCalories = test1List.stream().collect(summingInt(Test1::getAge));
        //连接字符串
        String shortName = test1List.stream().map(Test1::getName).collect(joining(", "));
        //广义的归约汇总
        int totalCalories1 = test1List.stream().collect(reducing(0, Test1::getAge, (i, j) -> i + j));
        //类似三元运算
        Optional<Test1> mostCalorie2 = test1List.stream().collect(reducing((t1, t2) -> t1.getAge() > t2.getAge() ? t1 : t2));
        //平均值
        double avgCalories = test1List.stream().collect(averagingDouble(Test1::getAge));
        //用reducing 连接字符串
        String shortTest = test1List.stream().map(Test1::getName).collect(reducing((s1, s2) -> s1 + s2)).orElse("");
        String shortTest1 = test1List.stream().collect(reducing("", Test1::getName, (s1, s2) -> s1 + s2));
        //分组
        Map<String, List<Test1>> dishesByName = test1List.stream().collect(groupingBy(Test1::getName));
        //复杂分组
        Map<CaloricLevel, List<Test1>> dishesByTest = test1List.stream().collect(groupingBy(t1 -> {
            if (t1.getAge() <= 2) {
                return CaloricLevel.DIET;
            } else if (t1.getAge() <= 5) {
                return CaloricLevel.NORMAL;
            } else {
                return CaloricLevel.FAT;
            }
        }));
        //多级分组
        Map<String, Map<Integer, Map<CaloricLevel, List<Test1>>>> dishesByNameCaloricLevel = test1List.stream().collect(
                groupingBy(Test1::getName, groupingBy(Test1::getAge, groupingBy(
                        t1 -> {
                            if (t1.getAge() <= 3) {
                                return CaloricLevel.DIET;
                            } else if (t1.getAge() <= 7) {
                                return CaloricLevel.NORMAL;
                            } else {
                                return CaloricLevel.FAT;
                            }
                        }
                        ))
                ));
        //按子组收集数据
        Map<String, Long> nameCount = test1List.stream().collect(groupingBy(Test1::getName, counting()));
        //把收集器的结果转换为另一种类型
        Map<String, Test1> mostCaloricByName = test1List.stream().collect(groupingBy(Test1::getName, collectingAndThen(maxBy(Comparator.comparingInt(Test1::getAge)), Optional::get)));
        //与groupingBy联合使用的其他收集器
        Map<String, Integer> totalCaloriesByName = test1List.stream().collect(groupingBy(Test1::getName, summingInt(Test1::getAge)));

        System.out.println("最大" + mostCalorie + "最小" + mostCalorie1 + "汇总" + totalCalories + "连接字符串" + shortName
                + "广义的归约汇总" + totalCalories1 + "类似三元运算" + mostCalorie2 + "平均值" + avgCalories +
                "用reducing 连接字符串" + shortTest + " _____ " + shortTest1 + "分组" + dishesByName + "复杂分组" + dishesByTest +
                "多级分组" + dishesByNameCaloricLevel + "按子组收集数据" + nameCount + "把收集器的结果转换为另一种类型" + mostCaloricByName +
                "与groupingBy联合使用的其他收集器" + totalCaloriesByName);


    }


    //使用流

    private static void test() {
        //(1) 给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？例如，给定[1, 2, 3, 4,5]，应该返回[1, 4, 9, 16, 25]。
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> sqares = numbers.stream().map(n -> n * n).collect(toList());
        System.out.println(sqares);

        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs = numbers1.stream().flatMap(i -> numbers2.stream().map(j -> new int[]{i, j})).collect(toList());
        List<int[]> pairs1 = numbers1.stream().flatMap(i -> numbers2.stream().filter(j -> (i + j) % 3 == 0).map(j -> new int[]{i, j})).collect(toList());
    }

    private static void test1() {
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        someNumbers.stream().map(x -> x * x).filter(x -> x % 2 == 0).findFirst().ifPresent(integer -> System.out.println("findFirst查找" + integer));
        int count = someNumbers.parallelStream().reduce(0, (a, b) -> a + b);
        int count1 = someNumbers.parallelStream().reduce(0, Integer::max);
        int count2 = someNumbers.parallelStream().reduce(0, Integer::min);
        System.out.println("求和" + count + "最大值" + count1 + "最小值" + count2);
    }

    private static void test3() {
        long uniqueWords = 0;
        try (Stream<String> lines =
                     Files.lines(Paths.get("D:\\kk.txt"), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
            System.out.println(uniqueWords);
        } catch (IOException e) {
        }

        Stream.generate(Math::random).limit(5).forEachOrdered(System.out::println);
        Stream.generate(Math::random).limit(5).forEach(System.out::println);
    }


}
