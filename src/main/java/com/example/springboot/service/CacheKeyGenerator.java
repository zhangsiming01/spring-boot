package com.example.springboot.service;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @program: spring-boot
 * @description: key 生成器
 * @author: zsm
 * @create: 2019-08-19 10:58
 **/
public interface CacheKeyGenerator {
    /***
     *  获取aop参数，生成指定的缓存key
     * @param pjp pjp
     * @return 缓存key
     */
    String getLockKey(ProceedingJoinPoint pjp);
}
