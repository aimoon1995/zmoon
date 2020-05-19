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

import java.util.Date;

import javax.validation.constraints.Max;

import com.project_study.my.common.entity.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * com.ithinkdt.web.quartz.entity.SysQuartzJobEntity.java

 * 系统任务触发器定义实体
 * @date 2018年10月16日
 * @author:YangDong
 * @version 1.0
 * @since JDK1.6
 */
@ApiModel(description = "任务触发器对象的提交参数")
public class SysQuartzTriggerEntity extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 3527003660394474165L;

    /**
     * cron表达式
     */
    @ApiModelProperty("cron表达式")
    @NotEmpty
    @Max(255)
    private String cronExpression;

    /**
     * 间隔时间，单位S
     */
    @ApiModelProperty("间隔时间，单位S")
    private String intervalTime;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Date endTime;

    /**
     * 下次开始时间
     */
    @ApiModelProperty("下次开始时间")
    private Date nextFireTime;

    /**
     * 预期开始时间
     */
    @ApiModelProperty("预期开始时间")
    private Date previousFireTime;

    /**
     * 触发器状态
     */
    private String triggerState;

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }
}
