package com.example.springboot.common.redis;

import java.lang.annotation.*;

/**
 * @program: spring-boot
 * @description: 锁的参数
 * @author: zsm
 * @create: 2019-08-19 10:55
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {

    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}
