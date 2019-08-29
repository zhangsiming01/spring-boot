package com.example.springboot.service;

import com.example.springboot.dal.model.ManageIpScheduleTask;

import java.util.List;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-08-19 16:45
 **/
public interface ManageIpScheduleTaskService {

    /***
     *  查询
     * @return
     */
    List<ManageIpScheduleTask> find();
}
