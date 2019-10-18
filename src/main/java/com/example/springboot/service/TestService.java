package com.example.springboot.service;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-09-02 09:59
 **/
public interface TestService<T,U,V,R> {
    /***
     *  自定义
     * @param t
     * @param u
     * @param v
     * @return
     */
    R apply(T t,U u,V v);
}
