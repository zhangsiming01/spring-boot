package com.example.springboot.java8.lambda.designpattern.responsibilitychain;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 11:15
 * @Description: 核心思想：每个处理环节，都有一个“指针”指向下一个处理者，类似链表一样。
 * 责任链模式
 * Processor接口：
 */
public interface Processor {
    /**
     * 指向下一个
     *
     * @return
     */
    Processor getNextProcessor();

    void process(String param);
}
