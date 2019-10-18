package com.example.springboot.java8.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @program: spring-boot
 * @description: 测试用分支/合并框架执行并行求和
 * @author: zsm
 * @create: 2019-09-06 10:58
 **/
public class Test3Main {
    public static void main(String[] args) {
        forkJoinSum();
    }

    private static List<Test3> getData() {
        List<Test3> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Test3 test3 = new Test3();
//            test3.setName1("name" + i);
//            test3.setName2("name" + i);
            test3.setNum1(i);
//            test3.setNum2(i + 2);
//            test3.setNum3(i + 3);
//            test3.setNum4(i + 4);
//            test3.setNum5(i + 5);
            list.add(test3);
        }
        return list;
    }

    /**
     * 用分支/合并框架执行并行求和
     *
     * @param
     * @return
     */
    private static Test3 forkJoinSum() {
        List<Test3> list = getData();
        ForkJoinSumCalculatorList task = new ForkJoinSumCalculatorList(list);
        return new ForkJoinPool().invoke(task);
    }
}
