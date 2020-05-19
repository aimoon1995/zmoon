package com.project_study.my.logs.entity;


import com.project_study.my.common.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class SystemLogEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("日志内容")
	private String logMessage;
	@ApiModelProperty("异常堆栈")
	private String stackTrace;
	@ApiModelProperty("logger")
	private String logger;
	@ApiModelProperty("日志等级")
	private String logLevel;
	@ApiModelProperty("日志用户")
	private String logUser;
	@ApiModelProperty("日志时间")
	private Date logTime;
	@ApiModelProperty("客户端ip")
	private String clientIp;
	@ApiModelProperty("服务器ip")
	private String serverIp;
	private String userName;
	private String personName;

	private Date logTimeStart;
	private Date logTimeEnd;
	
	public String getLogMessage() {
		return logMessage;
	}
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	public String getLogger() {
		return logger;
	}
	public void setLogger(String logger) {
		this.logger = logger;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getLogUser() {
		return logUser;
	}
	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public Date getLogTimeStart() {
		return logTimeStart;
	}
	public void setLogTimeStart(Date logTimeStart) {
		this.logTimeStart = logTimeStart;
	}
	public Date getLogTimeEnd() {
		return logTimeEnd;
	}
	public void setLogTimeEnd(Date logTimeEnd) {
		this.logTimeEnd = logTimeEnd;
	}

}
