/**
 * Copyright (c),2016-2018, iThinkDT
 * <br/>This program is protected by copyright laws;
 * <br/>Program Name: <b>ithinkdt-web-quartz<b>
 * <br/>
 * Package: com.ithinkdt.web.quartz.job <br/>
 * FileName: SimpleJob.java <br/>
 * <br/>
 * <br/>
 * <br/>2018年10月16日
 *
 * @author YangDong
 * @version 1.0
 * @since JDK 1.8
 */
package com.project_study.my.quartz.job;

import com.project_study.my.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;




/**
 * com.ithinkdt.web.quartz.job.SimpleJob.java
 *
 * 所有任务job类的基类，创建job时继承此类，实现了中断接口，子类调用isInterrupted()获取是否已中断。
 * <br>
 * 	<br>QRTZ_CALENDARS	以 Blob 类型存储 Quartz 的 Calendar 信息
	<br>QRTZ_CRON_TRIGGERS	存储 Cron Trigger，包括 Cron 表达式和时区信息
	<br>QRTZ_FIRED_TRIGGERS	存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息
	<br>QRTZ_PAUSED_TRIGGER_GRPS	存储已暂停的 Trigger 组的信息
	<br>QRTZ_SCHEDULER_STATE	存储少量的有关 Scheduler 的状态信息，和别的 Scheduler 实例(假如是用于一个集群中)
	<br>QRTZ_LOCKS	存储程序的非观锁的信息(假如使用了悲观锁)
	<br>QRTZ_JOB_DETAILS	存储每一个已配置的 Job 的详细信息
	<br>QRTZ_JOB_LISTENERS	存储有关已配置的 JobListener 的信息
	<br>QRTZ_SIMPLE_TRIGGERS	存储简单的 Trigger，包括重复次数，间隔，以及已触的次数
	<br>QRTZ_BLOG_TRIGGERS	Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，JobStore 并不知道如何存储实例的时候)
	<br>QRTZ_TRIGGER_LISTENERS	存储已配置的 TriggerListener 的信息
	<br>QRTZ_TRIGGERS	存储已配置的 Trigger 的信息
 * <br>
 * 
 * @date 2018年10月16日
 * @author:YangDong
 * @version 1.0
 * @since JDK1.6
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@Slf4j
public class SimpleJob extends QuartzJobBean implements InterruptableJob {

	/**
	 * 当前执行的线程，用于中断该任务时使用线程的中断方法
	 */
	private volatile boolean interrupted;
	private volatile Thread thread;

	public boolean isInterrupted () {
		return this.interrupted;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.InterruptableJob#interrupt()
	 */
	@Override
	public void interrupt() {
		interrupted = true;
		if (thread != null) {
			try {
				thread.interrupt();
			} finally {
				interrupted = false;
			}
		}
		log.info("进行任务中断操作");
	}
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		thread = Thread.currentThread();
		System.out.println(DateUtil.getSysDateString());
	}

}
