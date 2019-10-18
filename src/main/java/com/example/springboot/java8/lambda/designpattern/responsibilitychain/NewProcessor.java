package com.example.springboot.java8.lambda.designpattern.responsibilitychain;

import java.util.function.Consumer;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 13:17
 * @Description:
 */
@FunctionalInterface
public interface NewProcessor {
    Consumer<String> process(String param);
}

