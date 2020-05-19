/**
 * Copyright (c),2016-2018, iThinkDT
 * <br/>This program is protected by copyright laws; 
 * <br/>Program Name: <b>ithinkdt-web-quartz<b>
 * <br/>			 
 * Package: com.ithinkdt.web.quartz.config <br/>
 * FileName: QuartzSprinbBeanJobFactory.java <br/>
 *  <br/>
 * <br/> 
 * <br/>2018年10月16日
 * @author YangDong
 * @since JDK 1.8
 * @version 1.0
 */
package com.project_study.my.quartz.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * com.ithinkdt.web.quartz.config.QuartzSprinbBeanJobFactory.java

 * 自定义SpringBeanJobFactory，解决spring不能在quartz中注入bean的问题
 * @date 2018年10月16日
 * @author:YangDong
 * @version 1.0
 * @since JDK1.6
 */
public class QuartzSprinbBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
	/**
	 * 
	 */
	private transient AutowireCapableBeanFactory beanFactory;

	@Override
	public void setApplicationContext(final ApplicationContext context) {
		beanFactory = context.getAutowireCapableBeanFactory();
	}

	@Override
	protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
		final Object job = super.createJobInstance(bundle);
		beanFactory.autowireBean(job);
		return job;
	}

}
