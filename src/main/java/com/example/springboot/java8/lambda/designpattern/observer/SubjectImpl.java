package com.example.springboot.java8.lambda.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 10:57
 * @Description: subject 接口实现
 */
public class SubjectImpl implements Subject {

    private final List<Observer> list = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        list.add(observer);
    }

    @Override
    public void notifyAllObserver(String str) {
        list.forEach(c -> c.notify(str));
    }
}
