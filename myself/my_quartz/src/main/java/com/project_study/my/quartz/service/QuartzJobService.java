/**
 * Copyright (c),2016-2018, iThinkDT
 * <br/>This program is protected by copyright laws;
 * <br/>Program Name: <b>ithinkdt-web-quartz<b>
 * <br/>
 * Package: com.ithinkdt.web.quartz.service.impl <br/>
 * FileName: QuartzJobServiceImpl.java <br/>
 * <br/>
 * <br/>
 * <br/>2018年10月16日
 *
 * @author YangDong
 * @version 1.0
 * @since JDK 1.8
 */
package com.project_study.my.quartz.service;


import com.project_study.my.common.exception.BusinessException;
import com.project_study.my.common.utils.DateUtil;
import com.project_study.my.common.utils.StringUtil;
import com.project_study.my.common.utils.UUIDGenerator;

import com.project_study.my.quartz.constant.QuartzConstant;
import com.project_study.my.quartz.entity.SysQuartzJobEntity;
import com.project_study.my.quartz.entity.SysQuartzTriggerEntity;
import com.project_study.my.quartz.mapper.QuartzJobMapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * com.ithinkdt.web.quartz.service.impl.QuartzJobServiceImpl.java
 * <p>
 * 使用quartz job实现任务调管理和调度
 *
 * @version 1.0
 * 1创建 定时任务   2启动定时任务  3删除定时任务  4暂停定时任务
 * 5 恢复定时任务  6重启定时任务
 * @date 2018年10月16日
 * @author:zyl
 * @since JDK1.6
 */
@Service
@Slf4j
public class QuartzJobService {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Resource
    private QuartzJobMapper quartzJobMapper;

    /**
     * 获取指定class 限定的class对象
     *
     * @param className     package+className
     * @param isInitialized 是否初始化类
     * @return Class
     * @author YangDong 2018年10月16日 下午7:01:25
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends Job> loadClass(String className, boolean isInitialized) {
        try {
            return (Class<? extends Job>) ClassUtils.getClass(className, false);
        } catch (ClassNotFoundException e) {
            log.error("加载类失败 loadClass->{}", e);
        }
        return null;
    }


    /**
     * @param sysQuartzJobEntity
     * @return
     * @author YangDong 2018年10月16日 下午7:48:58
     */
    private static TriggerKey getTriggerKey(SysQuartzJobEntity sysQuartzJobEntity) {
        return TriggerKey.triggerKey(sysQuartzJobEntity.getJobName() + QuartzConstant.TRIGGER_SUFFIX, sysQuartzJobEntity.getJobGroup() + QuartzConstant.TRIGGER_SUFFIX);
    }

    private Scheduler getScheduler() {
        return schedulerFactoryBean.getScheduler();
    }

    /**
     * 创建任务
     *
     * @param sysQuartzJobEntity 系统任务定义实体
     * @return void
     */
    public void createJob(SysQuartzJobEntity sysQuartzJobEntity) {
        Scheduler scheduler = this.getScheduler();
        //根据jobGroup和jobName判断t_sys_quartz_job是否已经存在任务，已存在抛出异常
        checkJobExist(sysQuartzJobEntity);
        //根据JobGroup和JobName判断JobDetail是否存在数据，已存在抛出异常
        checkJobDetail(sysQuartzJobEntity, scheduler);
        //判断触发类型，如果按时间间隔触发则interval_time不为空，如果按指定时间调度触发则cron_expression不为空
        checkTriggerType(sysQuartzJobEntity);
        //组装数据并插入t_sys_quartz_job表中
        sysQuartzJobEntity.setId(UUIDGenerator.get());
        sysQuartzJobEntity.setCreateDate(DateUtil.getSysDate());
        sysQuartzJobEntity.setUpdateDate(DateUtil.getSysDate());
        sysQuartzJobEntity.setJobStatus(Integer.parseInt(QuartzConstant.JOB_STATUS_DISABLE));
        quartzJobMapper.insert(sysQuartzJobEntity);
    }

    /**
     * 修改定时任务
     *
     * @param sysQuartzJobEntity 系统任务定义实体
     * @return Integer 返回成功的行数
     */

    public Integer updateJob(SysQuartzJobEntity sysQuartzJobEntity) {
        Scheduler scheduler = this.getScheduler();
        //查询任务详情，不存在抛出异常，存在返回任务实体
        SysQuartzJobEntity quartzEntity = queryQuartzJob(sysQuartzJobEntity.getJobName(), sysQuartzJobEntity.getJobGroup());
        //判断触发类型，如果按时间间隔触发则interval_time不为空，如果按指定时间调度触发则cron_expression不为空
        checkTriggerType(sysQuartzJobEntity);
        //判断job是否可以修改(job的状态0：停用可以修改)
        checkUpdate(quartzEntity);
        sysQuartzJobEntity.setId(quartzEntity.getId());
        sysQuartzJobEntity.setUpdateDate(new Date());
        try {

            //构建一个新的trigger并更新
            Trigger trigger = builderTrigger(quartzEntity);
            scheduler.rescheduleJob(getTriggerKey(quartzEntity), trigger);
        } catch (SchedulerException e) {
            log.error("update job exception", e);
        }
        return quartzJobMapper.update(sysQuartzJobEntity);
    }

    /**
     * 查看任务详情
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return SysQuartzJobEntity 系统任务定义实体
     */

    public SysQuartzJobEntity getJobDetail(String jobName, String jobGroup) {
        //查询任务详情，不存在抛出异常，存在返回任务实体
        SysQuartzJobEntity quartzEntity = queryQuartzJob(jobName, jobGroup);
        Scheduler scheduler = this.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        //任务对应的触发器
        List<Trigger> triggers = new ArrayList<Trigger>();
        List<SysQuartzTriggerEntity> quartzTriggers = new ArrayList<SysQuartzTriggerEntity>();
        try {
            triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
            quartzTriggers = getTriggers(triggers, scheduler);
        } catch (SchedulerException e) {
            log.error("get jobDetail exception", e);
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0014)");
        }
        //将触发器加入到任务实体
        quartzEntity.setTriggers(quartzTriggers);
        return quartzEntity;
    }

    /**
     * 停用job任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return void
     */

    public void disableJob(String jobName, String jobGroup) {
        //查询任务详情，不存在抛出异常，存在返回任务实体
        SysQuartzJobEntity quartzEntity = queryQuartzJob(jobName, jobGroup);
        Scheduler scheduler = this.getScheduler();
        try {
            TriggerState state = scheduler.getTriggerState(getTriggerKey(quartzEntity));
            log.info("state is " + state.toString());
            JobKey key = JobKey.jobKey(quartzEntity.getJobName(), quartzEntity.getJobGroup());
            //非阻塞可以停用
            checkOperation(state, key);
            scheduler.deleteJob(key);
            //保留sys_quartz_job中的数据并更改任务为0:停用
            quartzEntity.setJobStatus(Integer.parseInt(QuartzConstant.JOB_STATUS_DISABLE));
            quartzJobMapper.updateJobStatus(quartzEntity);
        } catch (SchedulerException e) {
            log.error("disable job exception", e);
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0006, quartzEntity.getJobName()");
        }
    }

    /**
     * 删除job(判断任务状态是否是非阻塞状态)
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return TriggerState 状态
     */

    public TriggerState deleteJob(String jobName, String jobGroup) {
        //查询任务详情，不存在抛出异常，存在返回任务实体
        SysQuartzJobEntity quartzEntity = queryQuartzJob(jobName, jobGroup);
        Scheduler scheduler = this.getScheduler();
        try {
            TriggerState state = scheduler.getTriggerState(getTriggerKey(quartzEntity));
            log.info("state is " + state.toString());
            JobKey key = JobKey.jobKey(quartzEntity.getJobName(), quartzEntity.getJobGroup());
            //非阻塞的可以删除
            checkOperation(state, key);
            scheduler.deleteJob(key);
            quartzJobMapper.delete(quartzEntity.getId());
            return state;
        } catch (SchedulerException e) {
            log.error("delete job list exception", e);
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0012, quartzEntity.getJobName())");
        }
    }

    /**
     * 恢复job任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return void
     */

    public void resumeJob(String jobName, String jobGroup) {
        //查询任务详情，不存在抛出异常，存在返回任务实体
        SysQuartzJobEntity quartzEntity = queryQuartzJob(jobName, jobGroup);
        Scheduler scheduler = this.getScheduler();
        try {
            TriggerState state = scheduler.getTriggerState(getTriggerKey(quartzEntity));
            log.info("state is " + state.toString());
            JobKey key = JobKey.jobKey(quartzEntity.getJobName(), quartzEntity.getJobGroup());
            //非阻塞的可以恢复
            checkOperation(state, key);
            scheduler.resumeJob(key);
            //保留sys_quartz_job中的数据并更改任务为1:启用
            quartzEntity.setJobStatus(Integer.parseInt(QuartzConstant.JOB_STATUS_START));
            quartzJobMapper.updateJobStatus(quartzEntity);
        } catch (SchedulerException e) {
            log.error("resume job exception", e);
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0005, quartzEntity.getJobName())");
        }
    }

    /**
     * 暂停job任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return void
     */

    public void pauseJob(String jobName, String jobGroup) {
        //查询任务详情，不存在抛出异常，存在返回任务实体
        SysQuartzJobEntity quartzEntity = queryQuartzJob(jobName, jobGroup);
        Scheduler scheduler = this.getScheduler();
        try {
            TriggerState state = scheduler.getTriggerState(getTriggerKey(quartzEntity));
            log.info("state is " + state.toString());
            JobKey key = JobKey.jobKey(quartzEntity.getJobName(), quartzEntity.getJobGroup());
            //非阻塞的可以暂停
            checkOperation(state, key);
            scheduler.pauseJob(key);
            quartzEntity.setJobStatus(Integer.parseInt(QuartzConstant.JOB_STATUS_DISABLE));
            quartzJobMapper.updateJobStatus(quartzEntity);
        } catch (SchedulerException e) {
            log.error("pause job exception", e);
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0007, quartzEntity.getJobName())");
        }
    }

    /**
     * 中断job任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return void
     */

    public void interrupt(String jobName, String jobGroup) {
        //查询任务详情，不存在抛出异常，存在返回任务实体
        SysQuartzJobEntity quartzEntity = queryQuartzJob(jobName, jobGroup);
        Scheduler scheduler = this.getScheduler();
        try {
            scheduler.interrupt(JobKey.jobKey(quartzEntity.getJobName(), quartzEntity.getJobGroup()));
        } catch (UnableToInterruptJobException e) {
            log.error("interrupt job exception", e);
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0008, quartzEntity.getJobName())");
        }
    }

    /**
     * 移除触发器
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return Date 日期
     */
    public Date removeTrigger(String jobName, String jobGroup) {
        //查询任务详情，不存在抛出异常，存在返回任务实体
        SysQuartzJobEntity quartzEntity = queryQuartzJob(jobName, jobGroup);
        //构造一个新的trigger
        Trigger trigger = builderTrigger(quartzEntity);
        try {
            Scheduler scheduler = this.getScheduler();
            return scheduler.rescheduleJob(getTriggerKey(quartzEntity), trigger);
        } catch (SchedulerException e) {
            log.error("removeTrigger exception", e);
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0009)");
        }
    }

    /**
     * 暂停触发器
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return void
     */
    public void pauseTrigger(String jobName, String jobGroup) {
        //查询任务详情，不存在抛出异常，存在返回任务实体
        SysQuartzJobEntity quartzEntity = queryQuartzJob(jobName, jobGroup);
        Scheduler scheduler = this.getScheduler();
        try {
            scheduler.pauseTrigger(getTriggerKey(quartzEntity));
        } catch (SchedulerException e) {
            log.error("pauseTrigger exception", e);
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0010)");
        }
    }

    /**
     * job任务列表
     *
     * @param sysQuartzJobEntity 系统任务定义实体
     * @return List<SysQuartzJobEntity> 系统任务定义实体列表
     */
    public List<SysQuartzJobEntity> jobList(SysQuartzJobEntity sysQuartzJobEntity) {
        //根据条件查询任务列表
        List<SysQuartzJobEntity> quartzEntityList = quartzJobMapper.selectList(sysQuartzJobEntity);
        Scheduler scheduler = this.getScheduler();
        try {
            Map<String, List<Trigger>> map = new HashMap<String, List<Trigger>>();
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();
                    //任务对应的触发器,一个任务可能对应多个触发器，将任务触发器放入到map中
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    String key = jobName + jobGroup;
                    map.put(key, triggers);
                }
            }
            //循环任务列表quartzEntityList，根据key(jobName,jobGroup)取出任务对应的触发器列表并循环赋值到quartzTriggers中
            for (SysQuartzJobEntity quartzJobEntity : quartzEntityList) {
                String key = quartzJobEntity.getJobName() + quartzJobEntity.getJobGroup();
                List<Trigger> triggers = map.get(key);
                List<SysQuartzTriggerEntity> quartzTriggers = getTriggers(triggers, scheduler);
                quartzJobEntity.setTriggers(quartzTriggers);
            }
        } catch (SchedulerException e) {
            log.error("get job list exception", e);
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0011)");
        }
        return quartzEntityList;
    }


    /**
     * 获取触发器的开始时间，结束时间，下次开始时间，预计开始时间，状态，cron表达式及时间间隔
     *
     * @param triggers
     * @return List<SysQuartzTriggerEntity>
     */
    public List<SysQuartzTriggerEntity> getTriggers(List<Trigger> triggers, Scheduler scheduler) throws SchedulerException {
        List<SysQuartzTriggerEntity> quartzTriggers = new ArrayList<SysQuartzTriggerEntity>();
        if (triggers != null) {
            for (Trigger trigger : triggers) {
                SysQuartzTriggerEntity sysQuartzTriggerEntity = new SysQuartzTriggerEntity();
                //开始时间
                sysQuartzTriggerEntity.setStartTime(trigger.getStartTime());
                //结束时间
                sysQuartzTriggerEntity.setEndTime(trigger.getEndTime());
                //下次开始时间
                sysQuartzTriggerEntity.setNextFireTime(trigger.getNextFireTime());
                //预计开始时间
                sysQuartzTriggerEntity.setPreviousFireTime(trigger.getPreviousFireTime());
                //触发器的状态
                TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                sysQuartzTriggerEntity.setTriggerState(triggerState.name());
                //如果是按照cron表达式
                if (trigger instanceof CronTrigger) {
                    sysQuartzTriggerEntity.setCronExpression(((CronTrigger) trigger).getCronExpression());
                } else if (trigger instanceof SimpleTrigger) {
                    //如果是按照间隔时间
                    String repeatInterval = String.valueOf(((SimpleTrigger) trigger).getRepeatInterval());
                    sysQuartzTriggerEntity.setIntervalTime(repeatInterval);
                }
                quartzTriggers.add(sysQuartzTriggerEntity);
            }
        }
        return quartzTriggers;
    }

    /**
     * 启动任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return void
     * @author YangDong 2018年10月16日 下午6:59:21
     */
    public void startCronJob(String jobName, String jobGroup) {
        //查询任务详情，不存在抛出异常，存在返回任务实体
        SysQuartzJobEntity quartzEntity = queryQuartzJob(jobName, jobGroup);
        Scheduler scheduler = this.getScheduler();
        //根据JobGroup和JobName判断JobDetail是否存在数据,如果存在抛出异常
        checkJobDetail(quartzEntity, scheduler);
        //改变任务状态为1:启用
        quartzEntity.setJobStatus(Integer.parseInt(QuartzConstant.JOB_STATUS_START));
        quartzJobMapper.updateJobStatus(quartzEntity);
        Class<? extends Job> cls = loadClass(quartzEntity.getJobClassName(), false);
        if (cls == null) {
            throw new BusinessException("Class " + quartzEntity.getJobClassName() + " non-existent");
        }
        JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(quartzEntity.getJobName(), quartzEntity.getJobGroup()).build();
        // 表达式调度构建器(即任务执行的时间,每5秒执行一次)
        Trigger trigger = builderTrigger(quartzEntity);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("start job list exception", e);
        }
    }

    /**
     * 判断job是否可以操作(删除，停用，恢复)
     *
     * @param state 状态
     * @param key   jobName+jobGroup的key
     */
    public void checkOperation(TriggerState state, JobKey key) {
        if (TriggerState.BLOCKED == state) {
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0004, key.toString())");
        }
    }

    /**
     * 判断job是否可以修改(job的状态0：停用可以修改)，如果在启用中抛出异常
     *
     * @param quartzEntity 系统任务定义实体
     * @return void
     */
    public void checkUpdate(SysQuartzJobEntity quartzEntity) {
        Integer jobStatus = quartzEntity.getJobStatus();
        //如果是启用中
        if (QuartzConstant.JOB_STATUS_START.equals(Integer.toString(jobStatus))) {
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0003, quartzEntity.getJobName())");
        }
    }

    /**
     * 根据JobGroup和JobName判断t_sys_quartz_job中是否存在数据，已存在抛出异常
     *
     * @param sysQuartzJobEntity 系统任务定义实体
     * @return void
     */
    public void checkJobExist(SysQuartzJobEntity sysQuartzJobEntity) {
        SysQuartzJobEntity quartzEntity = quartzJobMapper.selectOneData(sysQuartzJobEntity);
        if (quartzEntity != null) {
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0001, quartzEntity.getJobName())");
        }
    }

    /**
     * 根据JobGroup和JobName判断JobDetail是否存在数据，已存在抛出异常
     *
     * @param sysQuartzJobEntity 系统任务定义实体
     * @return void
     */
    public void checkJobDetail(SysQuartzJobEntity sysQuartzJobEntity, Scheduler scheduler) {
        //根据jobGroup和jobName判断是否已经存在任务
        String group = sysQuartzJobEntity.getJobGroup();
        String jobName = sysQuartzJobEntity.getJobName();
        JobKey jobKey = JobKey.jobKey(jobName, group);
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0001, sysQuartzJobEntity.getJobName())");
            }
        } catch (SchedulerException e) {
            log.error("get job list exception", e);
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0013, jobName)");
        }
    }

    /**
     * 根据jobName (任务名)，jobGroup (任务组)查询任务详情，不存在抛出异常，存在返回任务实体
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return sysQuartzJobEntity 系统任务定义实体
     */
    public SysQuartzJobEntity queryQuartzJob(String jobName, String jobGroup) {
        SysQuartzJobEntity sysQuartzJobEntity = new SysQuartzJobEntity();
        sysQuartzJobEntity.setJobName(jobName);
        sysQuartzJobEntity.setJobGroup(jobGroup);
        SysQuartzJobEntity quartzEntity = quartzJobMapper.selectOneData(sysQuartzJobEntity);
        if (quartzEntity == null) {
            throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0002)");
        }
        return quartzEntity;
    }

    /**
     * 判断触发类型，如果按时间间隔触发则interval_time不为空，如果按指定时间调度触发则cron_expression不为空
     *
     * @param sysQuartzJobEntity
     * @return void
     */
    public void checkTriggerType(SysQuartzJobEntity sysQuartzJobEntity) {
        //如果triggerType为0(按时间间隔触发)，则interval_time不为空
        if (QuartzConstant.JOB_TRGGER_INTERVAL.equals(sysQuartzJobEntity.getTriggerType())) {
            if (sysQuartzJobEntity.getIntervalTime() == null) {
                throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0015, sysQuartzJobEntity.getJobName())");
            }
        }
        //如果triggerType为1(按指定时间调度),则cron_expression不为空
        if (QuartzConstant.JOB_TRGGER_CORN.equals(sysQuartzJobEntity.getTriggerType())) {
            if (StringUtils.isEmpty(sysQuartzJobEntity.getCronExpression())) {
                throw new BusinessException("getMessage(QuartzMsgNames.QUARTZ_MSG_BE0016, sysQuartzJobEntity.getJobName())");
            }
        }
    }

    /**
     * 构造一个新的trigger
     *
     * @param quartzEntity
     * @return Trigger 触发器
     */
    public Trigger builderTrigger(SysQuartzJobEntity quartzEntity) {
        // 表达式调度构建器(即任务执行的时间,每5秒执行一次)
        Trigger trigger = null;
        //按指定时间调度
        if (QuartzConstant.JOB_TRGGER_CORN.equals(quartzEntity.getTriggerType())) {
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzEntity.getCronExpression());
            // 按新的cronExpression表达式构建一个新的trigger
            trigger = TriggerBuilder.newTrigger().withIdentity(quartzEntity.getJobName() + QuartzConstant.TRIGGER_SUFFIX, quartzEntity.getJobGroup() + QuartzConstant.TRIGGER_SUFFIX).withSchedule(scheduleBuilder).build();
            if (StringUtil.isNotEmpty(quartzEntity.getJobData())) {
                JSONObject jsonObject = JSONObject.fromObject(quartzEntity.getJobData());
                Trigger finalTrigger = trigger;
                jsonObject.forEach((key, value) -> {
                    finalTrigger.getJobDataMap().put(key.toString(), value);
                });
                trigger = finalTrigger;
            }
        } else {
            //按时间间隔触发
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(quartzEntity.getIntervalTime());
            //按时间间隔构建一个新的trigger
            trigger = TriggerBuilder.newTrigger().withIdentity(quartzEntity.getJobName() + QuartzConstant.TRIGGER_SUFFIX, quartzEntity.getJobGroup() + QuartzConstant.TRIGGER_SUFFIX).withSchedule(simpleScheduleBuilder).build();
            if (StringUtil.isNotEmpty(quartzEntity.getJobData())) {
                JSONObject jsonObject = JSONObject.fromObject(quartzEntity.getJobData());
                Trigger finalTrigger = trigger;
                jsonObject.forEach((key, value) -> {
                    finalTrigger.getJobDataMap().put(key.toString(), value);
                });
                trigger = finalTrigger;
            }

        }
        return trigger;
    }

}
