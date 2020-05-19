package com.project_study.my.common.entity;

/**
 * 后端返回的错误信息实体
 */
public class ValidatorMessages {

	/** 错误消息 **/
	private String msg;

	/** 错误字段 **/
	private String fieldName;
	/**
	 * 字段名称
	 */
	private String fieldLabel;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the fieldLabel
	 */
	public String getFieldLabel() {
		return fieldLabel;
	}

	/**
	 * @param fieldLabel the fieldLabel to set
	 */
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
}