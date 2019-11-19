package com.example.springboot.java8.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangsiming
 * @Date: 2019/11/5 14:25
 * @Description:
 */
public class a {

    public static void main(String[] args) {
        List<b> list = new ArrayList<b>() {//这个大括号 就相当于我们  new 接口
            {//这个大括号 就是 构造代码块 会在构造函数前 调用
                add(new b(1, 2));
                add(new b(2, 2));
                add(new b(3, 2));
                add(new b(4, 2));
            }
        };
        for (b b : list) {
            System.out.println(b.getA() + "\t" + b.getB());
        }
    }
}

class b {
    private int a;
    private int b;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public b(int a, int b) {
        this.a = a;
        this.b = b;
    }
}
