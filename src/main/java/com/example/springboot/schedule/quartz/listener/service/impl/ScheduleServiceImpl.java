package com.example.springboot.schedule.quartz.listener.service.impl;

import com.example.springboot.common.enumutis.ScheduleStatus;
import com.example.springboot.dal.model.ManageIpScheduleTask;
import com.example.springboot.schedule.quartz.listener.service.ScheduleService;
import com.example.springboot.service.ManageIpScheduleTaskService;
import com.example.springboot.service.impl.ScheduleTaskNetwork;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.sasl.SaslClient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-08-19 16:17
 **/
@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Autowired
    private ManageIpScheduleTaskService manageIpScheduleTaskService;

    private Scheduler scheduler = null;

    /***
     * 暴露接口，唯一提供可调用的接口
     */
    @Override
    public void proceedSchedule() {
        try {
            this.proceedSchedule();
        } catch (Exception e) {
            logger.error("ScheduleServiceImpl proceedSchedule anomalous ->{}", e.getMessage());
        }
    }

    @Override
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    private void proceed() throws Exception {
        List<ManageIpScheduleTask> list = manageIpScheduleTaskService.find();
        if (null == list || list.isEmpty()) {
            return;
        }
        Set<String> currentScheduleCodeSet = this.getCurrentScheduleCodeSet();
        this.proceed(list, currentScheduleCodeSet);
    }

    /***
     *  启用任务
     * @param list
     * @param currentScheduleCodeSet
     */
    private void proceed(List<ManageIpScheduleTask> list, Set<String> currentScheduleCodeSet) throws Exception {
        for (ManageIpScheduleTask scheduleTask : list) {
            if (null == scheduleTask) {
                continue;
            }
            //启用任务
            ScheduleStatus scheduleStatus = ScheduleStatus.byCode(scheduleTask.getStatus());
            ScheduleStatus scheduleStop = ScheduleStatus.byCode(scheduleTask.getStatus());
            if (scheduleStatus == ScheduleStatus.START) {
                //未在进程中
                if (!this.isTaskCodeExist(scheduleTask.getTaskCode(), currentScheduleCodeSet)) {
                    this.startTask(scheduleTask);
                }
            }else if (scheduleStop == ScheduleStatus.STOP){
                if (this.isTaskCodeExist(scheduleTask.getTaskCode(),currentScheduleCodeSet)){
                    this.stopTask(scheduleTask);
                }
            }
        }
    }

    /****
     *  启用任务
     * @param scheduleTask
     * @throws Exception
     */
    private void startTask(ManageIpScheduleTask scheduleTask) throws Exception {
        if (!this.validate(scheduleTask)) {
            return;
        }
        ScheduleName scheduleName = new ScheduleName(scheduleTask);
        Class<? extends Job> clazz = this.getJobClass(scheduleName.getClassName());
        JobDetail jobDetail = this.getJobDetail(clazz, scheduleName.getJobName());
        Trigger trigger = this.getTrigger(scheduleName.getTriggerName(),scheduleName.getScheduleConfiguration());
        this.scheduler.scheduleJob(jobDetail,trigger);
    }

    /***
     *  停用任务
     * @param scheduleTask
     * @throws Exception
     */
    private void stopTask(ManageIpScheduleTask scheduleTask) throws Exception {
        if (!this.validate(scheduleTask)) {
            return;
        }
        ScheduleName scheduleName = new ScheduleName(scheduleTask);
        this.scheduler.unscheduleJob(TriggerKey.triggerKey(scheduleName.getTriggerName(), Scheduler.DEFAULT_GROUP));
        this.scheduler.deleteJob(JobKey.jobKey(scheduleName.getJobName(), Scheduler.DEFAULT_GROUP));
    }

    /***
     * 验证参数
     * @param schedule
     * @return
     */
    private boolean validate(ManageIpScheduleTask schedule) {
        if (null == schedule) {
            return false;
        } else if (null == schedule.getTaskCode()
                || null == schedule.getTaskName()
                || null == schedule.getTaskConf()
                || null == schedule.getTaskClass()
                || null == schedule.getTaskServerIp()) {
            return false;
        }
        if (!isLocalIp(schedule.getTaskServerIp())) {
            return false;
        }
        return true;

    }


    private boolean isTaskCodeExist(String taskCode, Set<String> set) {
        return set.contains(taskCode);
    }

    /***
     * 判断当前传入ip 是否是本机ip
     * @param ip
     * @return
     */
    private boolean isLocalIp(String ip) {
        if (null != ip && ip.length() >= 0) {
            return ScheduleTaskNetwork.isExist(ip);
        }
        return false;
    }

    private Set<String> getCurrentScheduleCodeSet() throws SchedulerException {
        Set<JobKey> jobKeySet = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(Scheduler.DEFAULT_GROUP));
        Set<String> scheduleCodeSet = new HashSet<>();
        for (JobKey jobKey : jobKeySet) {
            scheduleCodeSet.add(jobKey.getName());
        }
        return scheduleCodeSet;
    }

    /***
     * 必须返回 继承job的 class
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    private Class<? extends Job> getJobClass(String className) throws ClassNotFoundException {
        return (Class<? extends Job>) Class.forName(className);
    }

    /****
     *  获取jobDetail
     * @param clazz
     * @param jobName
     * @return
     */
    private JobDetail getJobDetail(Class<? extends Job> clazz, String jobName) {
        JobDetail jobDetail = newJob(clazz).withIdentity(jobName, Scheduler.DEFAULT_GROUP).build();
        return jobDetail;
    }

    /***
     *  获取 trigger
     * @param triggerName
     * @param conf
     * @return
     */
    private Trigger getTrigger(String triggerName, String conf) {
        CronTrigger cronTrigger = newTrigger().withIdentity(triggerName, Scheduler.DEFAULT_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule(conf)).startNow().build();
        return cronTrigger;
    }

    /**
     * 内部类，用于统一定义名称
     */
    class ScheduleName {
        private String taskCode = "taskCode";
        private String className;
        private String triggerName;
        private String jobName;
        private String scheduleConfiguration;

        ScheduleName(ManageIpScheduleTask scheduleTask) {
            if (null != scheduleTask.getTaskCode()) {
                this.taskCode = scheduleTask.getTaskCode();
            }
            this.className = scheduleTask.getTaskClass();
            this.triggerName = this.taskCode;
            this.jobName = this.taskCode;
            this.scheduleConfiguration = scheduleTask.getTaskConf();
        }

        public String getTaskCode() {
            return taskCode;
        }

        public void setTaskCode(String taskCode) {
            this.taskCode = taskCode;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getTriggerName() {
            return triggerName;
        }

        public void setTriggerName(String triggerName) {
            this.triggerName = triggerName;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getScheduleConfiguration() {
            return scheduleConfiguration;
        }

        public void setScheduleConfiguration(String scheduleConfiguration) {
            this.scheduleConfiguration = scheduleConfiguration;
        }
    }
}
