package com.project_study.my.logs.controller;


import com.project_study.my.common.ResultBean;
//import com.project_study.my.logs.appender.WebLogAspect;
//import com.project_study.my.logs.entity.BusinessLogEntity;
//import com.project_study.my.logs.entity.SystemLogEntity;
//import com.project_study.my.logs.service.LogService;
//import com.project_study.my.logs.service.LogStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日志模块
 * @author 浩
 *
 */
@Controller
@RequestMapping("/cer/bus/log")
@Api(description = "日志管理分系统日志管理和业务日志管理，系统日志需要对系统运行时异常进行记录，并支持筛选和查看；业务日志管理需要对系统业务流程的运转进行记录，支持扩展业务的日志记录", tags = {"日志管理"})
@Slf4j
public class LogController  {

//	@Autowired
//	private LogService logService;
//	@Resource
//	private LogStorageService logStorageService;
//	@Autowired
//	private WebLogAspect webLogAspect;

	/**
	 * 重新载入业务日志配置
	 * @return resultBean
	 */
	@PostMapping("/reload_business_log_config.do")
	@ResponseBody
	@ApiOperation(value = "重新载入业务日志配置", notes = "重新载入业务日志配置")
	public ResultBean reloadBusinessLogConfig() {
	    log.info("ceshi+________________________)+_)_)+)_+)+");
		//webLogAspect.initBizLogConfig();
		return ResultBean.success();
	}

//	/**
//	 * 模块列表
//	 * @param categoryId categoryId
//	 * @return ResultBean
//	 */
//	@PostMapping("/get_module_index.do")
//	@ResponseBody
//	@ApiOperation(value = "模块列表", notes = "根据类型ID(categoryId)查找模块列表")
//	@ApiImplicitParam(dataType = "String", name = "categoryId", value = "类型ID", required = true, paramType = "query")
//	public ResultBean getModuleIndex(String categoryId){
//		List result = logService.searchModuleIndex(categoryId);
//		return new ResultBean(result);
//	}
//
//	/**
//	 * 操作列表
//	 * @param moduleId 模块id
//	 * @return ResultBean
//	 */
//	@PostMapping("/get_operation_index.do")
//	@ResponseBody
//	@ApiOperation(value = "操作列表", notes = "根据模块ID(moduleId)获取操作属性")
//	@ApiImplicitParam(dataType = "String", name = "moduleId", value = "模块ID", required = true, paramType = "query")
//	public ResultBean getOperationIndex(String moduleId){
//		List result = logService.searchOperationIndex(moduleId);
//		return new ResultBean(result);
//	}
//
//	/**
//	 * 检索系统日志列表
//	 * @param condition 查询条件
//	 * @return 日志信息
//	 */
//	@PostMapping("/sys_log_list.do")
//	@ResponseBody
//	@ApiOperation(value = "检索系统日志列表", notes = "查询系统日志列表")
//	public ResultBean sysLogList(SystemLogEntity condition){
//		//测试
//		return logStorageService.searchSystemLog4Page(condition);
//	}
//
//	/**
//	 * 检索日志详情
//	 * @param id id
//	 * @return 日志信息
//	 */
//	@PostMapping("/sys_log_detail.do")
//	@ResponseBody
//	@ApiOperation(value = "检索日志详情", notes = "检索日志详情")
//	@ApiImplicitParam(dataType = "String", name = "id", value = "主键ID", required = true, paramType = "query")
//	public ResultBean sysLogDetail(String id){
//		return new ResultBean(logStorageService.searchSystemLogById(id));
//	}
//
//	/**
//	 * 检索业务日志列表
//	 * @param condition 查询条件
//	 * @return 日志信息
//	 */
//	@PostMapping("/bus_log_list.do")
//	@ResponseBody
//	@ApiOperation(value = "检索业务日志列表", notes = "检索业务日志列表")
//	public ResultBean busLogList(BusinessLogEntity condition){
//		return logStorageService.searchBusinessLog4Page(condition);
//	}

}
