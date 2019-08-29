package com.example.springboot.common.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @program: spring-boot
 * @description: 日志注解类  @AliasFor 主要是给注解的属性起名别的。
 * 让使用注解时，更加的容易理解（比如给value属性起别名）。一般上是配对别名。由于是Spring框架提供的
 * @author: zsm
 * @create: 2019-08-28 14:07
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
//只能在方法上使用该注解
@Target(ElementType.METHOD)
public @interface Log {

    /**
     * 日志描述，这里使用了@AliasFor 别名。spring 提供的
     *
     * @return
     */
    @AliasFor("desc")
    String value();

    /***
     * 日志描述
     * @return
     */
    @AliasFor("value")
    String desc() default "";

    /***
     * 是否不记录日志
     * @return
     */
    boolean ignore() default false;
}
