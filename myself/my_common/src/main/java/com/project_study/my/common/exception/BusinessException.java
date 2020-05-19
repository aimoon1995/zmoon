package com.project_study.my.common.exception;

/**
 * 业务Exception
 * 处理已知异常CASE，非系统异常
 * @author 浩
 *
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public BusinessException(String errorMsg) {
		super(errorMsg);
	}
	
	public BusinessException(String errorMsg, Throwable e) {
		super(errorMsg, e);
	}
	
	public BusinessException(String code, String errorMsg) {
		super(errorMsg);
		setCode(code);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
