package com.example.springboot.java8.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * @author: zhangsiming
 * @Date: 2019/11/6 13:44
 * @Description:
 */
public class aa {
    public static void main(String[] args) {
//        List<Integer> list1 = new ArrayList<>();
//        List<Integer> list2 = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            list1.add(i);
//        }
//
//
////        for (int i = 0; i < 3; i++) {
////            list2.add(i);
////        }
//
//        List<Integer> intersection = list1.stream().filter(str -> list2.contains(str)).collect(Collectors.toList());
//        for (Integer a : intersection) {
//            System.out.println(a);
//        }
        List<Test1> test1List = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Test1 test1 = new Test1();
//            test1.setAge(i);
//            test1.setName("name" + i);
//            test1List.add(test1);
//        }
//
//        test1List.parallelStream().filter(t -> t.getAge() > 5)
//                .sorted(comparing(Test1::getName)).map(Test1::getName).limit(3).collect(Collectors.toList());
        List<Integer> list9 = test1List.stream().map(test -> {
            return test.getAge();
        }).collect(Collectors.toList());

        for (Integer a : list9) {
            System.out.println(a);
        }
    }
}
