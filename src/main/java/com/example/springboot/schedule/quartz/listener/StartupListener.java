package com.example.springboot.schedule.quartz.listener;

import com.example.springboot.common.utis.StringUtils;
import com.example.springboot.schedule.quartz.listener.service.ScheduleService;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @program: spring-boot
 * @description: 定时任务扫描listener
 * @author: zsm
 * @create: 2019-08-19 15:58
 **/
@WebListener
@ConfigurationProperties(prefix = "quartz")
public class StartupListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);
    private String scan;
    private ScheduleService scheduleService;
    private static final String SCHEDULE_SERVICE_NAME = "scheduleService";
    private static final String SCHEDULER_NAME = "scheduler";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            this.initial(sce);
        } catch (Exception e) {
            logger.error("StartupListener anomalous ->{}", e.getMessage());
        }
    }

    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }

    /**
     * 用来启动定时器的
     *
     * @param servletContextEvent
     */
    private void initial(ServletContextEvent servletContextEvent) {
        Boolean isScan;
        if (StringUtils.isEmpty(this.getScan())) {
            isScan = true;
        } else {
            isScan = Boolean.parseBoolean(this.getScan());
        }
        if (isScan) {
            ServletContext context = servletContextEvent.getServletContext();
            ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
            this.scheduleService = (ScheduleService) applicationContext.getBean(SCHEDULE_SERVICE_NAME);
            Scheduler scheduler = (Scheduler) applicationContext.getBean(SCHEDULER_NAME);
            this.scheduleService.setScheduler(scheduler);
            this.scheduleService.proceedSchedule();
        }
    }
}

