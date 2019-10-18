package com.example.springboot.java8.lambda.designpattern.strategy;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-23 09:55
 * @Description: OrderService接口： 抽象接口 策略模式抽象接口
 * 策略模式包含三部分内容，
 *      一个代表某个算法的接口（它是策略模式的接口）。
 *      一个或多个该接口的具体实现，它们代表了算法的多种实现（比如，实体类Concrete-
 *      StrategyA或者ConcreteStrategyB）。
 *      一个或多个使用策略对象的客户。
 */
public interface OrderService {
    /**
     * 策略
     * @param str
     */
    void saveOrder(String str);
}
