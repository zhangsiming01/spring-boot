package com.example.springboot.web.controller.pattern;

import com.example.springboot.common.cache.Result;
import com.example.springboot.service.pattern.service.FactoryForStrategy;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-boot
 * @description: 策略模式+工厂模式
 * @author: zsm
 * @create: 2019-08-28 11:09
 **/
@RestController
@RequestMapping("/zsm")
public class StrategyController {

    @Autowired
    private FactoryForStrategy factoryForStrategy;
    @RequestMapping(value="strategy", method = RequestMethod.GET)
    @ApiOperation("策略模式+工厂模式")
    public Result<String> strategy(@ApiParam(value = "key",required = true) @RequestParam String key){
        String result;
        try {
            result = factoryForStrategy.getStrategy(key).doOperation();
        } catch (Exception e) {
            result = e.getMessage();
        }
        return Result.success(result);
    }
}
