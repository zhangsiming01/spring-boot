package com.example.springboot.java8.lambda.designpattern.dateutis;


import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.TemporalAdjusters.*;
/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-29 13:42
 * @Description: java8 时间处理
 */
public class Date8Time {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
        test9();
    }

    private static void test9() {
        LocalDate date1 = LocalDate.of(2014, 3, 18);
        System.out.println("date1：" + date1);
        LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY));
        System.out.println("date2：" + date2);
        LocalDate date3 = date2.with(lastDayOfMonth());
        System.out.println("date3：" + date3);
    }


    /**
     * 操纵、解析和格式化日期
     * 以相对方式修改LocalDate对象的属性
     */
    private static void test8() {
        LocalDate date = LocalDate.of(2018, 8, 21);
        System.out.println("date：" + date);
        LocalDate date1 = date.plusWeeks(1);
        System.out.println("date1：" + date1);
        LocalDate date2 = date.minusYears(3);
        LocalDate date4 = date.minusYears(-3);
        System.out.println("date4：" + date4);
        System.out.println("date2：" + date2);
        LocalDate date3 = date2.plus(6, ChronoUnit.MONTHS);
        System.out.println("date3：" + date3);
    }

    /**
     * 操纵、解析和格式化日期
     * 以比较直观的方式操纵LocalDate的属性
     */
    private static void test7() {
        LocalDate date = LocalDate.of(2018, 9, 29);
        System.out.println("date：" + date);
        LocalDate date1 = date.withYear(2019);
        System.out.println("date1：" + date1);
        LocalDate date2 = date.withMonth(10);
        System.out.println("date2：" + date2);
        LocalDate date3 = date.with(ChronoField.MONTH_OF_YEAR, 1);
        System.out.println("date3：" + date3);
    }

    /**
     * 定义 Duration 或 Period
     * 创建Duration和Period对象
     */
    private static void test6() {
        Duration threeMinutes = Duration.ofMinutes(3);
        System.out.println("threeMinutes" + threeMinutes);
        Duration threeMinutes1 = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println("threeMinutes1" + threeMinutes1);
        Period tenDays = Period.ofDays(10);
        System.out.println("tenDays" + tenDays);
        Period threeWeeks = Period.ofWeeks(3);
        System.out.println("threeWeeks" + threeWeeks);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
        System.out.println("twoYearsSixMonthsOneDay" + twoYearsSixMonthsOneDay);
    }

    /**
     * 机器的日期和时间格式
     * 通过向静态工厂方法ofEpochSecond传递一个代表秒数的值创建一个该类的实例。静
     * 态工厂方法ofEpochSecond还有一个增强的重载版本，它接收第二个以纳秒为单位的参数值，对
     * 传入作为秒数的参数进行调整。重载的版本会调整纳秒参数，确保保存的纳秒分片在0到999 999
     * 999之间。这意味着下面这些对ofEpochSecond工厂方法的调用会返回几乎同样的Instant对象：
     */
    private static void test5() {
        Instant instant = Instant.ofEpochSecond(3);
        System.out.println("instant:" + instant);
        Instant instant1 = Instant.ofEpochSecond(3, 0);
        System.out.println("instant1:" + instant1);
        //秒之后再加上100万纳秒（1秒）
        Instant instant2 = Instant.ofEpochSecond(2, 1_000_000_000);
        System.out.println("instant2:" + instant2);
        //4秒之前的100万纳秒（1秒）
        Instant instant3 = Instant.ofEpochSecond(4, -1_000_000_000);
        System.out.println("instant3:" + instant3);
    }

    /***
     * 合并日期和时间
     */
    private static void test4() {
        LocalTime time = LocalTime.of(20, 54, 12);
        LocalDate date = LocalDate.of(2019, 9, 29);
        LocalDateTime localDateTime = LocalDateTime.of(2019, Month.NOVEMBER, 29, 14, 16, 54);
        System.out.println("合并日期和时间1：" + localDateTime);
        LocalDateTime localDateTime1 = LocalDateTime.of(date, time);
        System.out.println("合并日期和时间2：" + localDateTime1);
        LocalDateTime localDateTime2 = date.atTime(time);
        System.out.println("合并日期和时间3：" + localDateTime2);
        LocalDateTime localDateTime3 = time.atDate(date);
        System.out.println("合并日期和时间4：" + localDateTime3);
        LocalDate date1 = localDateTime.toLocalDate();
        System.out.println("合并日期和时间,获取日期：" + date1);
        LocalTime time1 = localDateTime.toLocalTime();
        System.out.println("合并日期和时间,获取小时：" + time1);
    }

    /***
     * 创建LocalTime并读取其值
     */
    private static void test3() {
        //指定时间
        LocalTime time = LocalTime.of(20, 54, 12);
        System.out.println("指定时间:" + time);
        //获取小时
        int hour = time.get(ChronoField.HOUR_OF_DAY);
        System.out.println("获取小时:" + hour);
        //获取分钟
        int minute = time.get(ChronoField.MINUTE_OF_HOUR);
        System.out.println("获取分钟:" + minute);
        //获取秒
        int second = time.get(ChronoField.SECOND_OF_MINUTE);
        System.out.println("获取秒:" + second);
    }

    /**
     * 使用TemporalField读取LocalDate的值
     */
    private static void test2() {
        LocalDate date = LocalDate.of(2019, 9, 29);
        System.out.println("指定时间:" + date);
        int year = date.get(ChronoField.YEAR);
        System.out.println("获取年份:" + year);
        int month = date.get(ChronoField.MONTH_OF_YEAR);
        System.out.println("获取月份:" + month);
        int day = date.get(ChronoField.DAY_OF_MONTH);
        System.out.println("获取天数:" + day);
        int dateOfWeek = date.get(ChronoField.DAY_OF_WEEK);
        System.out.println("获取星期:" + dateOfWeek);
    }

    /**
     * 创建一个LocalDate对象并读取其值
     */
    private static void test1() {
        //指定时间
        LocalDate date = LocalDate.of(2018, 9, 29);
        System.out.println("指定时间:" + date);
        //获取年份
        int year = date.getYear();
        System.out.println("获取年份:" + year);
        //获取月份
        Month month = date.getMonth();
        System.out.println("获取月份:" + month);
        //获取天数
        int day = date.getDayOfMonth();
        System.out.println("获取天数:" + day);
        //获取星期几
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        System.out.println("获取星期几:" + dayOfWeek);
        //获取月份天数
        int len = date.lengthOfMonth();
        System.out.println("获取月份天数:" + len);
        //获取是否为闰年
        boolean leap = date.isLeapYear();
        System.out.println("获取是否为闰年:" + leap);
        //获取当前时间
        LocalDate today = LocalDate.now();
        System.out.println("获取当前时间:" + today);
    }
}
