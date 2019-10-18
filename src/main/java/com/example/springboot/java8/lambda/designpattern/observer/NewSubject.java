package com.example.springboot.java8.lambda.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 11:05
 * @Description: java8 重构后接口可以提供默认试下方法，
 */
public interface NewSubject {
    List<Observer> list = new ArrayList<>();

    default void registerObserver(Observer observer) {
        list.add(observer);
    }

    default void notifyAllObserver(String str) {
        list.forEach(c -> c.notify(str));
    }
}
