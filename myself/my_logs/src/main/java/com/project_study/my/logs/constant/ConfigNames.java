package com.project_study.my.logs.constant;

public interface ConfigNames {

	// 日志 TODO APPLICATION
	/** 业务日志等级 */
	String CONFIG_BUSINESS_LOG_LEVEL = "BUSINESS_LOG_LEVEL";
	/** 系统日志等级 */
	String CONFIG_SYSTEM_LOG_LEVEL = "SYSTEM_LOG_LEVEL";
	/** 系统日志报警等级 */
	String CONFIG_SYSTEM_LOG_NOTICE_LEVEL = "SYSTEM_LOG_NOTICE_LEVEL";
	/** 系统日志报警标题 */
	String CONFIG_SYSTEM_LOG_NOTICE_TITLE = "SYSTEM_LOG_NOTICE_TITLE";
	/** 系统日志报警邮件模板 */
	String CONFIG_SYSTEM_LOG_NOTICE_TEMPLATE = "SYSTEM_LOG_NOTICE_TEMPLATE";
	/** 系统日志报警接收者 */
	String CONFIG_SYSTEM_LOG_NOTICE_SEND_TO = "SYSTEM_LOG_NOTICE_SEND_TO";
}
