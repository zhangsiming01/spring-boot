package com.example.springboot.java8.lambda.designpattern.responsibilitychain;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 11:17
 * @Description: 抽象实现类
 */
public abstract class AbstractProcessor implements Processor {
    private Processor next;

    public AbstractProcessor(Processor processor) {
        this.next = processor;
    }


    @Override
    public Processor getNextProcessor() {
        return next;
    }

    @Override
    public abstract void process(String param);
}
