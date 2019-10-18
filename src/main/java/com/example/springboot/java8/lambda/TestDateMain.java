package com.example.springboot.java8.lambda;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @program: spring-boot
 * @description: Java8新增的DateTimeFormatter与SimpleDateFormat的区别
 * https://majing.io/questions/774
 * https://blog.csdn.net/tianlianye/article/details/79324765
 * https://yq.aliyun.com/articles/612637
 * @author: zsm
 * @create: 2019-09-13 10:08
 **/
public class TestDateMain {
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        //解析日期
        String dateStr = "2016年10月20日";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM日dd日");
//        LocalDate date1 = LocalDate.parse(dateStr, formatter);
//        System.out.println(date1);

        LocalDate date = LocalDate.now();

        System.out.println(date);

        System.out.println(date.format(DateTimeFormatter.ofPattern("d::MMM::uuuu")));
        System.out.println(date.format(DateTimeFormatter.BASIC_ISO_DATE));


        LocalDateTime dateTime = LocalDateTime.now();

        System.out.println(dateTime);

        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss")));
        System.out.println(dateTime.format(DateTimeFormatter.BASIC_ISO_DATE));

        Instant timestamp = Instant.now();

        System.out.println(timestamp);


        LocalDateTime dt = LocalDateTime.parse("27::四月::2014 21::39::48",
                DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss"));

        //日期转换为字符串
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh:mm:a");
        String nowStr = now.format(formatter1);
        System.out.println(nowStr);
        //ThreadLocal来限制SimpleDateFormat
        System.out.println(format(new Date()));
    }

    /**
     * 要在高并发环境下能有比较好的体验，可以使用ThreadLocal来限制SimpleDateFormat只能在线程内共享，
     * 这样就避免了多线程导致的线程安全问题。
     */
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static String format(Date date) {
        return threadLocal.get().format(date);
    }
}
