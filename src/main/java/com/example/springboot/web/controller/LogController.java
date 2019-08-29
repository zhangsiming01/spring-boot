package com.example.springboot.web.controller;

import com.example.springboot.common.annotation.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-boot
 * @description: 日志处理
 * @author: zsm
 * @create: 2019-08-28 14:33
 **/
@RestController
@RequestMapping("/zsm")
public class LogController {
    /**
     * 简单方法示例
     * @param hello
     * @return
     */
    @RequestMapping("/aop")
    @Log(value="请求了aopDemo方法")
    public String aopDemo(String hello) {
        return "请求参数为：" + hello;
    }

    /**
     * 不拦截日志示例
     * @param hello
     * @return
     */
    @RequestMapping("/notaop")
    @Log(value = "", ignore = true)
    public String notAopDemo(String hello) {
        return "此方法不记录日志，请求参数为：" + hello;
    }
}
