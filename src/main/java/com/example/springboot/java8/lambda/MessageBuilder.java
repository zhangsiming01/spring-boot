package com.example.springboot.java8.lambda;

/**
 * @program: spring-boot
 * @description: 使用Lambda必然需要一个函数式接口（只含有一个抽象方法的接口）
 * @author: zsm
 * @create: 2019-09-16 10:55
 **/
@FunctionalInterface
public interface MessageBuilder {
    /***
     * 函数式接口
     * @return
     */
    String buildMessage();
}
