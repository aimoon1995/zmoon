/**
 * Copyright (c),2016-2018, iThinkDT
 * <br/>This program is protected by copyright laws;
 * <br/>Program Name: <b>ithinkdt-web-quartz<b>
 * <br/>
 * Package: com.ithinkdt.web.quartz.controller <br/>
 * FileName: QuartzJobController.java <br/>
 * <br/>
 * <br/>
 * <br/>2018年10月16日
 *
 * @author YangDong
 * @version 1.0
 * @since JDK 1.8
 */
package com.project_study.my.quartz.controller;

import com.project_study.my.common.ResultBean;
import com.project_study.my.quartz.entity.SysQuartzJobEntity;
import com.project_study.my.quartz.service.QuartzJobService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

/**
 * com.ithinkdt.web.quartz.controller.QuartzJobController.java
 *
 * @version 1.0
 *  *      1创建 定时任务   2启动定时任务  3删除定时任务  4暂停定时任务
 *  *      5 恢复定时任务  6重启定时任务
 * @date 2018年10月16日
 * @author:YangDong
 * @since JDK1.6
 */
@Validated
@Api(description = "任务调度器管理", tags = {"任务调度"})
@RestController
@RequestMapping("/pub/quartz/job")
public class QuartzSchedulerController{

    /**
     * 定义任务管理接口
     */
    @Resource
    private QuartzJobService  quartzJobService;

    /**
     * 创建任务
     *
     * @param sysQuartzJobEntity 系统任务定义实体
     * @return ResultBean 结果
     */
    @PostMapping("/create")
    @ApiOperation(value = "创建任务", notes = "向t_sys_quartz_job表中插入一条数据(根据JobGroup和JobName判断t_sys_quartz_job表中是否已存在数据，判断JobDetail是否存在数据)")
    public ResultBean createJob(SysQuartzJobEntity sysQuartzJobEntity) {
       // UserEntity userentity = WebContext.getSessionUser();
        //if (userentity != null) {
            sysQuartzJobEntity.setCreateUser("userentity.getUserId()");
            sysQuartzJobEntity.setUpdateUser("userentity.getUserId()");
     //   }
        quartzJobService.createJob(sysQuartzJobEntity);
        return ResultBean.success();
    }

    /**
     * 修改定时任务
     *
     * @param sysQuartzJobEntity 系统任务定义实体
     * @return ResultBean 结果
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改定时任务", notes = "修改定时任务，[只支持修改表达式+jobData+triggerType]xiuga,判断job是否可以修改(job的状态0：停用可以修改,jobName和jobGroup不能被修改)")
    public ResultBean updateJob(SysQuartzJobEntity sysQuartzJobEntity) {
        quartzJobService.updateJob(sysQuartzJobEntity);
        return ResultBean.success();
    }

    /**
     * 查看任务详情
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return ResultBean
     */
    @PostMapping("/detail")
    @ApiOperation(value = "查看任务详情", notes = "查看任务详情(将任务对应的触发器加入到任务中)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobName", value = "任务名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobGroup", value = "任务组", required = true)})
    public ResultBean jobDetail(String jobName, String jobGroup) {
        return ResultBean.success().setData(quartzJobService.getJobDetail(jobName, jobGroup));
    }

    /**
     * 停用定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return ResultBean 结果
     */
    @PostMapping("/disable")
    @ApiOperation(value = "停用定时任务", notes = "停用定时任务(非阻塞可以停用，job_detail删掉，sys_quartz_job保留),数据库改动数据后停用再启动即可")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobName", value = "任务名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobGroup", value = "任务组", required = true)})
    public ResultBean disableJob(String jobName, String jobGroup) {
        quartzJobService.disableJob(jobName, jobGroup);
        return ResultBean.success();
    }

    /**
     * 删除job
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return ResultBean 结果
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除job", notes = "删除job(非阻塞的可以删除)，在停用的基础上，删除了t_sys_quartz_job表数据")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobName", value = "任务名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobGroup", value = "任务组", required = true)})
    public ResultBean deleteJob(String jobName, String jobGroup) {
        return ResultBean.success().setData(quartzJobService.deleteJob(jobName, jobGroup));
    }

    /**
     * 恢复定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return ResultBean 结果
     */
    @PostMapping("/resume")
    @ApiOperation(value = "恢复定时任务", notes = "恢复定时任务(非阻塞的可以恢复)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobName", value = "任务名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobGroup", value = "任务组", required = true)})
    public ResultBean resumeJob(String jobName, String jobGroup) {
        quartzJobService.resumeJob(jobName, jobGroup);
        return ResultBean.success();
    }

    /**
     * 暂停任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return ResultBean 结果
     */
    @PostMapping("/pause_job")
    @ApiOperation(value = "暂停任务", notes = "暂停任务(非阻塞的可以暂停)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobName", value = "任务名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobGroup", value = "任务组", required = true)})
    public ResultBean pauseJob(String jobName, String jobGroup) {
        quartzJobService.pauseJob(jobName, jobGroup);
        return ResultBean.success();
    }

    /**
     * 中断任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return ResultBean 结果
     */
    @PostMapping("/interrupt")
    @ApiOperation(value = "中断任务", notes = "中断任务")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobName", value = "任务名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobGroup", value = "任务组", required = true)})
    public ResultBean interruptJob(String jobName, String jobGroup) {
        quartzJobService.interrupt(jobName, jobGroup);
        return ResultBean.success();
    }

    /**
     * 对指定的任务移除已经添加的触发器
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return ResultBean 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "对指定的任务移除已经添加的触发器", notes = "对指定的任务移除已经添加的触发器")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobName", value = "任务名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobGroup", value = "任务组", required = true)})
    public ResultBean removeTrigger(String jobName, String jobGroup) {
        quartzJobService.removeTrigger(jobName, jobGroup);
        return ResultBean.success();
    }

    /**
     * 暂停触发器
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return ResultBean 结果
     */
    @PostMapping("/pause_trigger")
    @ApiOperation(value = "暂停触发器", notes = "暂停触发器")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobName", value = "任务名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobGroup", value = "任务组", required = true)})
    public ResultBean pauseTrigger(String jobName, String jobGroup) {
        quartzJobService.pauseTrigger(jobName, jobGroup);
        return ResultBean.success();
    }

    /**
     * job任务列表
     *
     * @param sysQuartzJobEntity 系统任务定义实体
     * @return ResultBean 结果
     */
    @PostMapping("/list")
    @ApiOperation(value = "job任务列表", notes = "job任务列表")
    public ResultBean jobList(SysQuartzJobEntity sysQuartzJobEntity) {
        return ResultBean.success().setData(quartzJobService.jobList(sysQuartzJobEntity));
    }

    /**
     * 启动job
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return ResultBean 结果
     */
    @PostMapping("/start")
    @ApiOperation(value = "启动job", notes = "启动job")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobName", value = "任务名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "jobGroup", value = "任务组", required = true)})
    public ResultBean startJob(String jobName,
                               String jobGroup) {
        quartzJobService.startCronJob(jobName, jobGroup);
        return ResultBean.success();
    }

}
