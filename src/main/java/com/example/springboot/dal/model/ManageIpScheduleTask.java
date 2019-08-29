package com.example.springboot.dal.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "manage_ip_schedule_task")
public class ManageIpScheduleTask implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 任务名称编码
     */
    @Column(name = "task_code")
    private String taskCode;

    /**
     * 任务名
     */
    @Column(name = "task_name")
    private String taskName;

    /**
     * 任务执行表达式
     */
    @Column(name = "task_conf")
    private String taskConf;

    /**
     * 任务执行类
     */
    @Column(name = "task_class")
    private String taskClass;

    /**
     * 任务执行的服务器
     */
    @Column(name = "task_server_ip")
    private String taskServerIp;

    /**
     * 任务状态0:启用;1：禁用
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    /**
     * 获取id
     *
     * @return id - id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取任务名称编码
     *
     * @return task_code - 任务名称编码
     */
    public String getTaskCode() {
        return taskCode;
    }

    /**
     * 设置任务名称编码
     *
     * @param taskCode 任务名称编码
     */
    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    /**
     * 获取任务名
     *
     * @return task_name - 任务名
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名
     *
     * @param taskName 任务名
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取任务执行表达式
     *
     * @return task_conf - 任务执行表达式
     */
    public String getTaskConf() {
        return taskConf;
    }

    /**
     * 设置任务执行表达式
     *
     * @param taskConf 任务执行表达式
     */
    public void setTaskConf(String taskConf) {
        this.taskConf = taskConf;
    }

    /**
     * 获取任务执行类
     *
     * @return task_class - 任务执行类
     */
    public String getTaskClass() {
        return taskClass;
    }

    /**
     * 设置任务执行类
     *
     * @param taskClass 任务执行类
     */
    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    /**
     * 获取任务执行的服务器
     *
     * @return task_server_ip - 任务执行的服务器
     */
    public String getTaskServerIp() {
        return taskServerIp;
    }

    /**
     * 设置任务执行的服务器
     *
     * @param taskServerIp 任务执行的服务器
     */
    public void setTaskServerIp(String taskServerIp) {
        this.taskServerIp = taskServerIp;
    }

    /**
     * 获取任务状态0:启用;1：禁用
     *
     * @return status - 任务状态0:启用;1：禁用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置任务状态0:启用;1：禁用
     *
     * @param status 任务状态0:启用;1：禁用
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}