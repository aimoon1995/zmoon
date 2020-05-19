package com.project_study.my.logs.entity;

import com.project_study.my.common.entity.BaseEntity;

/**
 * 业务日志配置类
 */
public class BusinessLogConfigEntity extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 日志模块
	 */
	private String module;
	/**
	 * 业务日志标题
	 */
	private String title;
	/**
	 * 业务日志描述
	 */
	private String description;
	/**
	 * 业务日志切面
	 */
	private String pointcut;
	/**
	 * 业务日志类名
	 */
	private String className;

	/**
	 * 业务日志方法名
	 */
	private String methodName;
	/**
	 * 业务日志内容
	 */
	private String msg;
	/**
	 * 业务日志参数
	 */
	private String params;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPointcut() {
		return pointcut;
	}

	public void setPointcut(String pointcut) {
		this.pointcut = pointcut;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
}
