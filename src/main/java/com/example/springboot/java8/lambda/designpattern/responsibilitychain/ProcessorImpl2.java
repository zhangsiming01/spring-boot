package com.example.springboot.java8.lambda.designpattern.responsibilitychain;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 11:23
 * @Description: 具体实现类2
 */
public class ProcessorImpl2 extends AbstractProcessor {
    public ProcessorImpl2(Processor processor) {
        super(processor);
    }

    @Override
    public void process(String param) {
        System.out.println("processor 2 is processing:" + param);
        if (getNextProcessor() != null) {
            getNextProcessor().process(param);
        }
    }
}
