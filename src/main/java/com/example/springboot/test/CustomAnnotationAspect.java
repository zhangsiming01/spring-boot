package com.example.springboot.test;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Author: zhagnsiming
 * @CreateDate: 2019-03-25 9:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-03-25 9:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Aspect
@Component
public class CustomAnnotationAspect {
    @Before("@annotation(ds)")
    public void println(JoinPoint point, CustomAnnotation ds) throws Exception{
        System.out.println("我是测试与验证注解的方法的---before--"+ds.name());
    }

    @After("@annotation(ds)")
    public void println1(JoinPoint point,CustomAnnotation ds){
        System.out.println("我是测试与验证注解的方法的---after--"+ds.name());
    }
}
