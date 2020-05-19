package com.project_study.my.common.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtil {
	
	private static MessageSourceAccessor messageSourceAccessor;
	
	public static MessageSourceAccessor geMessageSourceAccessort() {
		if(messageSourceAccessor == null){
			messageSourceAccessor = new MessageSourceAccessor((MessageSource)SpringUtils.getBean("messageSource"));
		}
		return  messageSourceAccessor;
	}
	
	public static final String getMessage(String code, String... args){
		MessageSourceAccessor msa = geMessageSourceAccessort();
		if(msa != null){
			return msa.getMessage(code, args, code);
		}else{
			return code;
		}
	}
	
	/**
	 * 获取国际化信息
	 * @param note 中文注释
	 * @param code 国际化code
	 * @param args 国际化参数
	 * @return 消息
	 */
	public static final String getMsgAndNote(String note, String code, String... args) {
		return getMessage(note, args);
	}
}
