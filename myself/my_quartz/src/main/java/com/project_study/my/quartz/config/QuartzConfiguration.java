/**
 * Copyright (c),2016-2018, iThinkDT
 * <br/>This program is protected by copyright laws; 
 * <br/>Program Name: <b>ithinkdt-web-quartz<b>
 * <br/>			 
 * Package: com.ithinkdt.web.quartz.config <br/>
 * FileName: QuartzConfiguration.java <br/>
 *  <br/>
 * <br/> 
 * <br/>2018年10月16日
 * @author YangDong
 * @since JDK 1.8
 * @version 1.0
 */
package com.project_study.my.quartz.config;

import java.util.Properties;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * com.ithinkdt.web.quartz.config.QuartzConfiguration.java

 * quartz启动配置类
 * @date 2018年10月16日
 * @author:YangDong
 * @version 1.0
 * @since JDK1.6
 */
@Configuration
@ConditionalOnClass(com.alibaba.druid.pool.DruidDataSource.class)
public class QuartzConfiguration {

	public static final String QUARTZ_PROPERTIES_PATH = "/conf/quartz.properties";

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		QuartzSprinbBeanJobFactory jobFactory = new QuartzSprinbBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DruidDataSource dataSource) {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setStartupDelay(20);
		schedulerFactoryBean.setQuartzProperties(quartzProperties());
		schedulerFactoryBean.setOverwriteExistingJobs(true);
		schedulerFactoryBean.setJobFactory(jobFactory);
		schedulerFactoryBean.setDataSource(dataSource);
		return schedulerFactoryBean;
	}

	public Properties quartzProperties() {
		PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
		factoryBean.setLocation(new ClassPathResource(QUARTZ_PROPERTIES_PATH));
		try {
			factoryBean.afterPropertiesSet();
			return factoryBean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
