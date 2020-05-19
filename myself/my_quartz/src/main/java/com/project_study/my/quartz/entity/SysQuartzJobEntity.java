/**
 * Copyright (c),2016-2018, iThinkDT
 * <br/>This program is protected by copyright laws;
 * <br/>Program Name: <b>ithinkdt-web-quartz<b>
 * <br/>
 * Package: com.ithinkdt.web.quartz.entity <br/>
 * FileName: SysQuartzJobEntity.java <br/>
 * <br/>
 * <br/>
 * <br/>2018年10月16日
 *
 * @author YangDong
 * @version 1.0
 * @since JDK 1.8
 */
package com.project_study.my.quartz.entity;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.project_study.my.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * com.ithinkdt.web.quartz.entity.SysQuartzJobEntity.java

 * 系统任务定义实体
 * @date 2018年10月16日
 * @author:YangDong
 * @version 1.0
 * @since JDK1.6
 */
@ApiModel(description = "任务对象的提交参数")
public class SysQuartzJobEntity extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 3527003660394474165L;



    /**
     * 任务名
     */
    @ApiModelProperty("任务名")
    private String jobName;

    /**
     * 任务组
     */
    @ApiModelProperty("任务组")

    private String jobGroup;

    /**
     * cron表达式
     */
    @ApiModelProperty("cron表达式")
    private String cronExpression;

    /**
     * 任务全路径名称
     */
    @ApiModelProperty("任务全路径名称")
    private String jobClassName;

    /**
     *一个blob字段，存放持久化job对象
     */
    @ApiModelProperty("一个blob字段，存放持久化job对象")
    private String jobData;

    /**
     * 任务描述
     */
    @ApiModelProperty("任务描述")
    private String jobDescription;

    /**
     * 启用和停用
     */
    @ApiModelProperty("启用和停用")
    private Integer jobStatus;

    /**启用和停用**/
    @ApiModelProperty("启用和停用")
    private  String jobStatusDesc;

    /**
     * 0:按时间间隔触发, 1:按指定时间调度
     */
    @ApiModelProperty("0:按时间间隔触发, 1:按指定时间调度")
    private String triggerType;

    /**0:按时间间隔触发, 1:按指定时间调度**/
    @ApiModelProperty("0:按时间间隔触发, 1:按指定时间调度")
    private  String triggerTypeDesc;

    /**
     * 间隔时间，单位S
     */
    @ApiModelProperty("间隔时间，单位S")
    private Integer intervalTime;

    /**
     * 指定job运行的主机，用于集群模式下，如果有值，当前job只能在此机器上运行
     */
    @ApiModelProperty("指定job运行的主机")
    private String executingHostName;

    /**
     * 任务对应的触发器
     */
    @ApiModelProperty("任务对应的触发器")
    @Valid
    @NotNull
    private List<SysQuartzTriggerEntity> triggers;

    /**
     * @return the jobName
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName the jobName to set
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @return the jobGroup
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * @param jobGroup the jobGroup to set
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * @return the cronExpression
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * @param cronExpression the cronExpression to set
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * @return the jobClassName
     */
    public String getJobClassName() {
        return jobClassName;
    }

    /**
     * @param jobClassName the jobClassName to set
     */
    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    /**
     * @return the jobData
     */
    public String getJobData() {
        return jobData;
    }

    /**
     * @param jobData the jobData to set
     */
    public void setJobData(String jobData) {
        this.jobData = jobData;
    }

    /**
     * @return the jobDescription
     */
    public String getJobDescription() {
        return jobDescription;
    }

    /**
     * @param jobDescription the jobDescription to set
     */
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    /**
     * @return the jobStatus
     */
    public Integer getJobStatus() {
        return jobStatus;
    }

    /**
     * @param jobStatus the jobStatus to set
     */
    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobStatusDesc() {
        return jobStatusDesc;
    }

    public void setJobStatusDesc(String jobStatusDesc) {
        this.jobStatusDesc = jobStatusDesc;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getTriggerTypeDesc() {
        return triggerTypeDesc;
    }

    public void setTriggerTypeDesc(String triggerTypeDesc) {
        this.triggerTypeDesc = triggerTypeDesc;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    /**
     * @return the executingHostName
     */
    public String getExecutingHostName() {
        return executingHostName;
    }

    /**
     * @param executingHostName the executingHostName to set
     */
    public void setExecutingHostName(String executingHostName) {
        this.executingHostName = executingHostName;
    }

    public List<SysQuartzTriggerEntity> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<SysQuartzTriggerEntity> triggers) {
        this.triggers = triggers;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}
