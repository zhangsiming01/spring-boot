package com.example.springboot.service.pattern.service;

import com.example.springboot.common.enumutis.ErrorCodeConstants;
import com.example.springboot.common.utis.UnifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: spring-boot
 * @description: 创建策略工厂
 * @author: zsm
 * @create: 2019-08-28 10:56
 **/
@Service
public class FactoryForStrategy {
    /***
     * 使用concurrentHashMap是防止多线程操作
     */
    @Autowired
    Map<String,Strategy> strategys = new ConcurrentHashMap<>();

    public Strategy getStrategy(String component){
        Strategy strategy = strategys.get(component);
        if(strategy == null){
            throw new UnifiedException(ErrorCodeConstants.PATTERN01);
        }
        return strategy;
    }

}
