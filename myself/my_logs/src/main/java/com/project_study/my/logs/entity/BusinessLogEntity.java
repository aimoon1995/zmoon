package com.project_study.my.logs.entity;

import com.project_study.my.common.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class BusinessLogEntity extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("业务id")
	private String businessId;
	@ApiModelProperty("业务key")
	private String businessKey;
	@ApiModelProperty("模块id")
	private String moduleId;
	@ApiModelProperty("模块名称")
	private String moduleName;
	@ApiModelProperty("操作")
	private String operation;
	@ApiModelProperty("日志内容")
	private String logMessage;
	@ApiModelProperty("日志用户")
	private String logUser;
	private String userName;
	private String personName;
	@ApiModelProperty("日志时间")
	private Date logTime;
	@ApiModelProperty("客户端ip")
	private String clientIp;
	@ApiModelProperty("服务器ip")
	private String serverIp;
	@ApiModelProperty("原数据")
	private String oldData;
	@ApiModelProperty("新数据")
	private String newData;
	private String categoryId;
	private Date logTimeStart;
	private Date logTimeEnd;
	
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getLogMessage() {
		return logMessage;
	}
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
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
	public String getOldData() {
		return oldData;
	}
	public void setOldData(String oldData) {
		this.oldData = oldData;
	}
	public String getNewData() {
		return newData;
	}
	public void setNewData(String newData) {
		this.newData = newData;
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
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
