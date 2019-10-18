package com.example.springboot.java8.lambda.designpattern.function;

import java.util.stream.LongStream;

/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-29 15:30
 * @Description: 函数式编程计算阶乘
 */
public class FunctionalProgramming {
    public static void main(String[] args) {
        //迭代式的阶乘计算
        int r = factorialIterative(2);
        System.out.println("迭代式的阶乘计算" + r);
        //递归式的阶乘计算
        long n = factorialRecursive(2);
        System.out.println("递归式的阶乘计算" + n);
        //基于Stream的阶乘
        long l = factorialStreams(2);
        System.out.println("基于Stream的阶乘" + l);
        //基于“尾-递”的阶乘
        long l1 = factorialTailRecursive(2);
        System.out.println("基于“尾-递”的阶乘" + l1);
    }

    /**
     * 基于“尾-递”的阶乘
     *
     * @param n
     * @return
     */
    private static long factorialTailRecursive(long n) {
        return factorialHelper(1, n);
    }

    static long factorialHelper(long acc, long n) {
        return n == 1 ? acc : factorialHelper(acc * n, n - 1);
    }

    /**
     * 基于Stream的阶乘
     *
     * @param n
     * @return
     */
    private static long factorialStreams(long n) {
        return LongStream.rangeClosed(1, n).reduce(1, (long a, long b) -> a * b);
    }

    /**
     * 递归式的阶乘计算
     *
     * @param n
     * @return
     */
    private static long factorialRecursive(long n) {
        return n == 1 ? 1 : n * factorialRecursive(n - 1);
    }

    /***
     * 迭代式的阶乘计算
     * @param i
     * @return
     */
    private static int factorialIterative(int i) {
        int r = 1;
        for (int j = 1; j <= i; j++) {
            r *= j;
        }
        return r;
    }

}
