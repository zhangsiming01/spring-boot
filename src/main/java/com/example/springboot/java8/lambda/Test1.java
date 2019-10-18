package com.example.springboot.java8.lambda;

import lombok.Data;
import lombok.ToString;

/**
 * @program: spring-boot
 * @description: Lambda 测试一
 * @author: zsm
 * @create: 2019-08-30 14:11
 **/
@Data
@ToString
public class Test1 {

    private String name;

    private Integer age;

    public static boolean getData1(Test1 test1) {
        if (test1.getAge() > 5) {
            return true;
        }
        return false;
    }
}
