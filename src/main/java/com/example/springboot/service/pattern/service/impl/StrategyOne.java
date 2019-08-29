package com.example.springboot.service.pattern.service.impl;

import com.example.springboot.service.pattern.service.Strategy;
import org.springframework.stereotype.Component;

/**
 * @program: spring-boot
 * @description:  实现类
 * @author: zsm
 * @create: 2019-08-28 11:07
 **/
@Component("strategyOne")
public class StrategyOne implements Strategy {
    @Override
    public String doOperation() {
        return "StrategyOne";
    }
}
