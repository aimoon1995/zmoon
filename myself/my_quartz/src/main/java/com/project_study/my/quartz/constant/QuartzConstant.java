/**
 * Copyright (c),2016-2018, iThinkDT
 * <br/>This program is protected by copyright laws; 
 * <br/>Program Name: <b>ithinkdt-web-quartz<b>
 * <br/>			 
 * Package: com.ithinkdt.web.quartz.constant <br/>
 * FileName: QuartzConstant.java <br/>
 *  <br/>
 * <br/> 
 * <br/>2018年10月16日
 * @author YangDong
 * @since JDK 1.8
 * @version 1.0
 */
package com.project_study.my.quartz.constant;


/**
 * com.ithinkdt.web.quartz.constant.QuartzConstant.java

 * 
 * @date 2018年10月16日
 * @author:YangDong
 * @version 1.0
 * @since JDK1.6
 */
public interface QuartzConstant {

	/**
	 * 触发器的name和group后缀
	 */
	public static final String TRIGGER_SUFFIX = "_trigger";

	/**
	 * 0：停用
	 */
	public static final String JOB_STATUS_DISABLE = "0";

	/**
	 * 1：启用
	 */
	public static final String JOB_STATUS_START = "1";

	/**
	 * 0：按时间间隔触发
	 */
	public static final String JOB_TRGGER_INTERVAL = "0";

	/**
	 * 1：按指定时间调度
	 */
	public static final String JOB_TRGGER_CORN = "1";


}
