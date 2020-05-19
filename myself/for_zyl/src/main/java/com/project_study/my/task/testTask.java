package com.project_study.my.task;

import com.project_study.my.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @ClassName com.project_study.my.task.testTask
 * @Description: TODO
 * @Author zyl
 * @Date 2019/12/30
 * @Version V1.0
 **/
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@Slf4j
public class testTask  extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
                     String param1 = (String) jobExecutionContext.getMergedJobDataMap().get("system");
         log.debug("=======================task------------------------"+jobExecutionContext.getJobDetail()+ DateUtil.format(new Date(), DateUtil.DATE_FORMAT_DATETIME_S_HMSS));
    }
}

