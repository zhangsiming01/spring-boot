package com.example.springboot.schedule.quartz.listener;

import com.example.springboot.schedule.quartz.listener.service.impl.ScheduleJobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-08-21 17:49
 **/
@Configuration
public class QuartzConfig {
    @Bean
    public ScheduleJobFactory getScheduleJobFactory(){
        return new ScheduleJobFactory();
    }

    public Properties quartzProperties(){
        Properties properties = new Properties();
        //默认的jobFactory
        properties.setProperty("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
        // 默认的线程池
        properties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        // 目前线程数量
        properties.setProperty("org.quartz.threadPool.threadCount", "5");
        return properties;
    }

    @Bean(name = "scheduler")
    public SchedulerFactoryBean getSchedulerFactoryBean(){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        //注入自定义工厂类
        factoryBean.setJobFactory(getScheduleJobFactory());
        factoryBean.setQuartzProperties(quartzProperties());
        return factoryBean;
    }
}
