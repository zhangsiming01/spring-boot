package com.example.springboot.service.impl;

import com.example.springboot.dal.mapper.ManageIpScheduleTaskMapper;
import com.example.springboot.dal.model.ManageIpScheduleTask;
import com.example.springboot.service.ManageIpScheduleTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-08-19 16:46
 **/
@Service("manageIpScheduleTaskService")
public class ManageIpScheduleTaskServiceImpl implements ManageIpScheduleTaskService {
    @Autowired
    private ManageIpScheduleTaskMapper manageIpScheduleTaskMapper;
    @Override
    public List<ManageIpScheduleTask> find() {
        return manageIpScheduleTaskMapper.selectAll();
    }
}
