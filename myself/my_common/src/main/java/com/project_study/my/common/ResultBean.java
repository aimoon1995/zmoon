package com.project_study.my.common;


import com.project_study.my.common.enums.ErrorCodeEnums;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 
 * @author wanghao
 * 
 * 所有的异常信息，只传入消息编码，不会去对消息ID从资源文件中取消息内容操作 2018-09-26 12:30:03 update yangdong
 * 
 */
@ApiModel("统一返回类型")
public class ResultBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 541111297236898043L;

	@ApiModelProperty(value = "状态")
	private boolean status = true;
	//代码(错误代码)
	private ErrorCodeEnums code = ErrorCodeEnums.SUCCESSFUL;

	@ApiModelProperty(value = "提示消息")
	private String messages;

	@ApiModelProperty(value = "返回数据")
	private T data;
	// 当前页
	@ApiModelProperty(value = "当前页")
	private int page;
	// 总条数
	@ApiModelProperty(value = "总条数")
	private int record;
	// 总页数
	@ApiModelProperty(value = "总页数")
	private int total;
	
	public ResultBean(){
		
	}
	
	public ResultBean(T data){
		this.data = data;
	}

	public static <T> ResultBean<T> success() {
		return success("");
	}

	public static <T> ResultBean<T> error() {
		return error("");
	}

	public static <T> ResultBean<T> success(String message) {
		ResultBean<T> result = new ResultBean<T>();
		result.setMessages(message);
		return result;
	}

	public static <T> ResultBean<T> error(String message) {
		ResultBean<T> result = new ResultBean<T>();
		result.setStatus(false);
		result.setCode(ErrorCodeEnums.BUSINESS_EXCEPTION);
		if(!StringUtils.isEmpty(message)){
			result.setMessages(message);
		}
		return result;
	}

	public static <T> ResultBean<T> result(Boolean bool) {
		if (bool) {
			return ResultBean.success("处理成功");
		} else {
			return ResultBean.error("处理失败");
		}
	}

	public static <T> ResultBean<T> result(Boolean result, String message) {
		if (result) {
			return ResultBean.success(message);
		} else {
			return ResultBean.error(message);
		}
	}

	public T getData() {
		return data;
	}

	public ResultBean<T> setData(T data) {
		this.data = data;
		return this;
	}

	public String getMessages() {
		return messages;
	}

	public ResultBean<T> setMessages(String messages) {
		this.messages = messages;
		return this;
	}

	public boolean isStatus() {
		return status;
	}

	public ResultBean<T> setStatus(boolean status) {
		this.status = status;
		return this;
	}

	public int getPage() {
		return page;
	}

	public ResultBean<T> setPage(int page) {
		this.page = page;
		return this;
	}

	public int getRecord() {
		return record;
	}

	public ResultBean<T> setRecord(int record) {
		this.record = record;
		return this;
	}

	public int getTotal() {
		return total;
	}

	public ResultBean<T> setTotal(int total) {
		this.total = total;
		return this;
	}

	/**
	 * @return the com.code
	 */
	public ErrorCodeEnums getCode() {
		return code;
	}

	/**
	 * @param code the com.code to set
	 */
	public ResultBean<T> setCode(ErrorCodeEnums code) {
		this.code = code;
		return this;
	}
}
