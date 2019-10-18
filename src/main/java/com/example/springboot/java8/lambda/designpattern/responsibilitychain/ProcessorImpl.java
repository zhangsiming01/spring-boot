package com.example.springboot.java8.lambda.designpattern.responsibilitychain;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 11:20
 * @Description: 定义具体的实现类
 */
public class ProcessorImpl extends AbstractProcessor {
    public ProcessorImpl(Processor processor) {
        super(processor);
    }

    @Override
    public void process(String param) {
        System.out.println("processor 1 is processing:" + param);
        if (getNextProcessor() != null) {
            getNextProcessor().process(param);
        }
    }
}
