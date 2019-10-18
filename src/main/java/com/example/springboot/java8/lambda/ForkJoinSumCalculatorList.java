package com.example.springboot.java8.lambda;


import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-09-06 11:04
 **/
public class ForkJoinSumCalculatorList extends RecursiveTask<Test3> {
    /**
     * 要求和的数组
     */
    private final List<Test3> numbers;

    /**
     * 子任务处理的数组的起始值位置
     */
    private final int start;

    /***
     * 子任务处理的数组的终止值位置
     */
    private final int end;

    private final Test3 test3= new Test3();

    /***
     * 不在将任务分解为子任务的数组大小
     */
    public static final long THRESHOLD = 6;

    final Test4 test4 = new Test4();

    /**
     * 公共构造函数用于创建主任务
     *
     * @param numbers
     */
    public ForkJoinSumCalculatorList(List<Test3> numbers) {
        this(numbers, 0, numbers.size());
    }

    /**
     * 私有构造函数用于以递归方式为主任务创建子任务
     *
     * @param numbers
     * @param start
     * @param end
     */
    private ForkJoinSumCalculatorList(List<Test3> numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Test3 compute() {
        int length = end - start;
        if (length < THRESHOLD) {
            return computeSequentially();
        }
        int mid = (end + start) / 2;
//        int mid = start + length / 2;
        ForkJoinSumCalculatorList leftTask = new ForkJoinSumCalculatorList(numbers, start, mid);
        leftTask.fork();
        ForkJoinSumCalculatorList rightTask = new ForkJoinSumCalculatorList(numbers, mid, end);
        final Test3 compute = rightTask.compute();
        final Test3 join = leftTask.join();
        return compute;
    }

    public Test3 computeSequentially() {
//        Integer num2Sum = 0;
        Integer num1Sum = 0;
//        Integer num3Sum = 0;
//        Integer num4Sum = 0;
//        Integer num5Sum = 0;
        for (int i = start; i < end; i++) {
            num1Sum += numbers.get(i).getNum1();
//            num2Sum += numbers.get(i).getNum2();
//            num3Sum += numbers.get(i).getNum3();
//            num4Sum += numbers.get(i).getNum4();
//            num5Sum += numbers.get(i).getNum5();
        }

        test3.setNum1(num1Sum);
//        test4.setNum2Sum(num2Sum);
//        test4.setNum3Sum(num3Sum);
//        test4.setNum4Sum(num4Sum);
//        test4.setNum5Sum(num5Sum);
        return test3;
    }
}
