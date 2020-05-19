package com.project_study.my.logs.constant;

public interface DbConstants {

	//日志表
	public final static String SQL_LOG_INSERT_BUSINESS_LOG = "Log.insertBusinessLog";
	public final static String SQL_LOG_INSERT_SYSTEM_LOG = "Log.insertSystemLog";
	public final static String SQL_LOG_SEARCH_BUSINESS_LOG = "Log.searchBusinessLog";
	public final static String SQL_LOG_SEARCH_SYSTEM_LOG = "Log.searchSystemLog";
	public final static String SQL_LOG_SEARCH_BUSINESS_LOG_COUNT = "Log.searchBusinessLogCount";
	public final static String SQL_LOG_SEARCH_SYSTEM_LOG_COUNT = "Log.searchSystemLogCount";
	public final static String SQL_LOG_SEARCH_SYSTEM_LOG_BY_ID = "Log.searchSystemLogById";
	public final static String SQL_LOG_INDEX_INSERT = "Log.insertLogIndex";
	public final static String SQL_LOG_INDEX_SEARCH = "Log.searchLogIndex";
	public final static String SQL_LOG_INDEX_SEARCH_CATEGORY_INDEX = "Log.searchCategory";
	public final static String SQL_LOG_INDEX_SEARCH_MODULE_INDEX = "Log.searchModuleByCategory";
	public final static String SQL_LOG_INDEX_SEARCH_OPERATION_INDEX = "Log.searchOperationByModule";
	public final static String SQL_LOG_SEARCH_BUSINESS_LOG_CONFIG = "Log.searchBusinessLogConfig";
}
