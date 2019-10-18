package com.example.springboot.java8.lambda.designpattern.observer;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 10:53
 * @Description: Subject 接口
 */
public interface Subject {

    void registerObserver(Observer observer);

    void notifyAllObserver(String str);

}
