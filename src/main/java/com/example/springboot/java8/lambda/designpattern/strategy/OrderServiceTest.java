package com.example.springboot.java8.lambda.designpattern.strategy;

/**
 * @Auther: zhangsiming
 * @Date: 2019-09-23 10:16
 * @Description:
 */
public class OrderServiceTest {

    public static void main(String[] args) {
        //Java8 之前
        System.out.println("java8 之前");
        OrderServiceExecutor executor = new OrderServiceExecutor(new MySqlSaveOrderStrategy());
        executor.save("001");
        OrderServiceExecutor executor1 = new OrderServiceExecutor(new NoSqlSaveOrderStrategy());
        executor.save("002");
        //Java8
        System.out.println("java8 之后");
        OrderServiceExecutor executor2 = new OrderServiceExecutor((String str) -> System.out.println("order:" + str + "save to mysql"));
        executor2.save("003");
        OrderServiceExecutor executor3 = new OrderServiceExecutor((String str) -> System.out.println("order:" + str + "save to noSql"));
        executor2.save("004");
    }
}
