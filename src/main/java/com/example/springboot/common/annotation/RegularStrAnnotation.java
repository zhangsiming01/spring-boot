package com.example.springboot.common.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author: zhagnsiming
 * @CreateDate: 2019-04-02 13:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-04-02 13:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 定义针对String类型的自定义注解
 * 一、注解的基础
1.注解的定义：Java文件叫做Annotation，用@interface表示。
2.元注解：@interface上面按需要注解上一些东西，包括@Retention、@Target、@Document、@Inherited四种。
3.注解的保留策略：
　　@Retention(RetentionPolicy.SOURCE)   // 注解仅存在于源码中，在class字节码文件中不包含
　　@Retention(RetentionPolicy.CLASS)     // 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得
　　@Retention(RetentionPolicy.RUNTIME)  // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
4.注解的作用目标：
　　@Target(ElementType.TYPE)                      // 接口、类、枚举、注解
　　@Target(ElementType.FIELD)                     // 字段、枚举的常量
　　@Target(ElementType.METHOD)                 // 方法
　　@Target(ElementType.PARAMETER)            // 方法参数
　　@Target(ElementType.CONSTRUCTOR)       // 构造函数
　　@Target(ElementType.LOCAL_VARIABLE)   // 局部变量
　　@Target(ElementType.ANNOTATION_TYPE) // 注解
　　@Target(ElementType.PACKAGE)               // 包
5.注解包含在javadoc中：
　　@Documented
6.注解可以被继承：
　　@Inherited
7.注解解析器：用来解析自定义注解。
 */
//作用于的类型，此处为对象的属性
@Target(FIELD)
//运行时生效
@Retention(RUNTIME)
//通过类实现注解的相关校验操作
@Constraint(validatedBy = RegularStrVerification.class)
public  @interface RegularStrAnnotation {
    //必填，检验未通过时的提示信息
    String message() default "请填写正确的信息";
    //必填，下文会将到此处参数的作用 分组
    Class<?>[] groups() default{};
    //必填 负载
    Class<? extends Payload>[] payload() default {};
    //选填，验证参数
    String verification() default  "";
    //选填，验证参数
    int max() default 0;

    //指定多个时使用
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        RegularStrAnnotation[] value();
    }
}
