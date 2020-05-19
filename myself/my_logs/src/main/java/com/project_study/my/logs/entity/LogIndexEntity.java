package com.project_study.my.logs.entity;

import com.project_study.my.common.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class LogIndexEntity extends BaseEntity {
	@ApiModelProperty("类型ID")
	private String categoryId = "";
	@ApiModelProperty("类型名称")
	private String categoryName = "";
	@ApiModelProperty("模块ID")
	private String moduleId = "";
	@ApiModelProperty("模块名称")
	private String moduleName = "";
	@ApiModelProperty("操作")
	private String operation = "";
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
}
