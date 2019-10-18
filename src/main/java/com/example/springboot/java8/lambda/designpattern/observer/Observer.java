package com.example.springboot.java8.lambda.designpattern.observer;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 10:52
 * @Description: Observer 接口
 * 基于某个Subject主题，然后一堆观察者Observer注册到主题上，有事件发生时，subject根据注册列表，去通知所有的observer。
 * https://www.cnblogs.com/yjmyzz/p/refactor-design-pattern-using-java8.html
 */
public interface Observer {
    void notify(String str);
}
