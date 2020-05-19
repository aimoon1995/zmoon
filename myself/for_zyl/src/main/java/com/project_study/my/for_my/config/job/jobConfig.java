package com.project_study.my.for_my.config.job;

import com.project_study.my.task.testTask;
import org.quartz.JobDataMap;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

/**
 * @ClassName jobConfig
 * @Description: TODO
 * @Author zyl
 * @Date 2019/12/30
 * @Version V1.0
 **/
@Component
public class jobConfig {


    //info物料bom
    @Bean("job4")
    public JobDetailFactoryBean bomJobDetailFactoryBean(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(testTask.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("param1","param1");
        jobDataMap.put("param2","param2");
        jobDetailFactoryBean.setJobDataMap(jobDataMap);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }
}
