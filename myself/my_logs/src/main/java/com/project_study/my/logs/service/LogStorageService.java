package com.project_study.my.logs.service;

import com.project_study.my.common.ResultBean;
import com.project_study.my.logs.entity.BusinessLogEntity;
import com.project_study.my.logs.entity.SystemLogEntity;
import com.project_study.my.logs.mapper.LogStorageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 日志持久化服务
 * @author 浩
 *
 */
@Service
@Transactional
public class LogStorageService {
         @Resource
		private LogStorageMapper logStorageMapper;


	/**
	 * 分页检索系统日志
	 * @param condition
	 * @return
	 */
	ResultBean searchSystemLog4Page(SystemLogEntity condition) {
	 return  null;
	};



	/**
	 * 存储系统日志
	 * @param entity
	 */
	public void doSystemLog(SystemLogEntity entity) {
		logStorageMapper.insertSystemLog(entity);
	};

}