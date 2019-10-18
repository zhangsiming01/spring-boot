package com.example.springboot.java8.lambda.designpattern.templatemethod;

import java.util.function.Consumer;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-24 10:25
 * @Description: 显然如果模板的实现方式越多，子类就越多。使用java8重构后，可以把上面的3个模板（包括抽象类模板）减少到1个
 */
public class PushTemplateLambda {

    public void push(int customerId, String shopName, Consumer<Object[]> execute) {
        System.out.println("准备推送...");
        Object[] param = new Object[]{customerId, shopName};
        execute.accept(param);
        System.out.println("推送完成\n");
    }
}
