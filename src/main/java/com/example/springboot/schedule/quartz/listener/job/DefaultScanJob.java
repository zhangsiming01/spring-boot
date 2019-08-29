package com.example.springboot.schedule.quartz.listener.job;

import com.example.springboot.schedule.quartz.listener.StartupListener;
import com.example.springboot.schedule.quartz.listener.service.ScheduleService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-08-19 16:23
 **/
public class DefaultScanJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("starting default scan job...");
        try {
            this.scheduleService.proceedSchedule();
        } catch (Exception e) {
            throw new JobExecutionException();
        }
    }
}
