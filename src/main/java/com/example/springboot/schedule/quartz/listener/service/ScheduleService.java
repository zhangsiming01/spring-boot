package com.example.springboot.schedule.quartz.listener.service;

import org.quartz.Scheduler;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-08-19 16:15
 **/
public interface ScheduleService {

    /***
     *  执行定时器
     */
    void proceedSchedule();

    /***
     * 设置
     * @param scheduler
     */
    void setScheduler(Scheduler scheduler);
}
