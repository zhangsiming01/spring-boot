package com.example.springboot.common.redis;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: spring-boot
 * @description: 锁的注解
 * @author: zsm
 * @create: 2019-08-19 10:46
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheLock {

    /***
     *  redis 锁key 的前缀
     * @return redis 锁key 的前缀
     */
    String prefix() default "";

    /***
     *  过期秒数，默认为5秒
     * @return 轮询锁时间
     */
    int expire() default 5;

    /**
     * 超时时间
     *
     * @return 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /***
     *  <p>key 的分隔符（默认：）<p/>
     *  <p>生成的key：N：S01008:500</p>
     * @return String
     */
    String delimiter() default ":";
}
