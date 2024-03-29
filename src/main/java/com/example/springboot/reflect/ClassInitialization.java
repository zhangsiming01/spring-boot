package com.example.springboot.reflect;

import java.util.Random;

/**
 * @author: zhangsiming
 * @Date: 2019/10/28 13:56
 * @Description:
 */
public class ClassInitialization {
    public static Random rand = new Random(47);

    public static void main(String[] args) throws ClassNotFoundException {
        /***
         * 字面常量获取方式获取Class对象
         */
        Class initAble = InitAble.class;
        System.out.println("After creating InitAble ref");
        /**
         * 不触发类初始化
         */
        System.out.println(InitAble.staticFinal);
        /**
         * 会触发类初始化
         */
        System.out.println(InitAble.staticFinal2);
        /***
         * 会触发类初始化
         */
        System.out.println(InitAble2.staticNonFinal);

        Class initAble3 = Class.forName("com.example.springboot.reflect.InitAble3");
        System.out.println("After creating InitAble3 ref");
        System.out.println(InitAble3.staticNonFinal);
    }
}

class InitAble {
    /**
     * 编译期静态常量
     */
    static final int staticFinal = 47;
    /**
     * 非编期静态常量
     */
    static final int staticFinal2 = ClassInitialization.rand.nextInt(1000);

    static {
        System.out.println("Initializing InitAble");
    }
}

class InitAble2 {
    /***
     * 静态成员变量
     */
    static int staticNonFinal = 147;

    static {
        System.out.println("Initializing InitAble2");
    }
}

class InitAble3 {
    /***
     * 静态成员变量
     */
    static int staticNonFinal = 74;

    static {
        System.out.println("Initializing InitAble3");
    }
}