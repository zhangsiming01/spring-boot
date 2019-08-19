package com.example.springboot.common.utis.performance;

import java.util.*;

/**
 * @Author: zhagnsiming
 * @CreateDate: 2018-12-28 10:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2018-12-28 10:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ListDistinctTest {
//    //set集合去重，不打乱顺序
//    public static void main(String[] args){
//        List<String> list  =   new  ArrayList<String>();
//        list.add("aaa");
//        list.add("bbb");
//        list.add("aaa");
//        list.add("aba");
//        list.add("aaa");
//
//        Set set = new  HashSet();
//        List newList = new  ArrayList();
//        for (String cd:list) {
//            if(set.add(cd)){
//                newList.add(cd);
//            }
//        }
//        System.out.println( "去重后的集合： " + newList);
//    }

//    //遍历后判断赋给另一个list集合
//    public static void main(String[] args){
//        List<String> list  =   new  ArrayList<String>();
//        list.add("aaa");
//        list.add("bbb");
//        list.add("aaa");
//        list.add("aba");
//        list.add("aaa");
//
//        List<String> newList = new  ArrayList<String>();
//        for (String cd:list) {
//            if(!newList.contains(cd)){
//                newList.add(cd);
//            }
//        }
//        System.out.println( "去重后的集合： " + newList);
//    }

    //set去重
//    public static void main(String[] args){
//        List<String> list  =   new  ArrayList<String>();
//        list.add("aaa");
//        list.add("bbb");
//        list.add("aaa");
//        list.add("aba");
//        list.add("aaa");
//
//        Set set = new  HashSet();
//        List newList = new  ArrayList();
//        set.addAll(list);
//        newList.addAll(set);
//
//        System.out.println( "去重后的集合： " + newList);
//    }
    //set去重(缩减为一行)
    public static void main(String[] args){
        List<String> list  =   new  ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("aba");
        list.add("aaa");

        List newList = new ArrayList(new HashSet(list));

        System.out.println( "去重后的集合： " + newList);
    }
}
