package com.project_study.my.logs.appender;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import com.project_study.my.common.PatternMessageSource;
import com.project_study.my.common.exception.BusinessException;
import com.project_study.my.common.utils.MessageUtil;
import com.project_study.my.common.utils.SpringUtils;
import com.project_study.my.common.utils.StringUtil;
import com.project_study.my.logs.entity.SystemLogEntity;
import com.project_study.my.logs.processor.LogProcessor;


/**
 * Log4j系统日志DB引擎
 * @author 浩
 */
public class SystemLogAppender extends AppenderBase<LoggingEvent> {

//	@Override
//	public void close() {
//		// TODO Auto-generated method stub
//
//	}

//	@Override
//	public boolean requiresLayout() {
//		// TODO Auto-generated method stub
//		return false;
//	}
	@Override
	protected void append(LoggingEvent event) {
		if(SpringUtils.getApplicationContext() == null) {
			return;
		}
		// 过滤其它日志
//		if (event.getLoggerName().equals(WebLogAspect.class.getName())){
//			return;
//		}
		// 过滤业务异常
//		if (event.getThrowableProxy() != null && ((ThrowableProxy)event.getThrowableProxy()).getThrowable() instanceof BusinessException) {
//			return;
//		}
		if (StringUtil.getOrElse(event.getLevel()).equals("ERROR")) {
			SystemLogEntity entity = new SystemLogEntity();
			entity.setLogger(event.getLoggerName());
			entity.setLogLevel(StringUtil.getOrElse(event.getLevel()));
			String defaultMsg = "Undefined Error";
			if(!PatternMessageSource.class.getName().equals(event.getLoggerName())){
				defaultMsg = MessageUtil.getMessage("undefined.error");
			}
			entity.setLogMessage(StringUtil.getOrElse(event.getMessage(), defaultMsg));
			entity.setStackTrace(StringUtil.stackTraceToString(event.getThrowableProxy() != null ? event.getThrowableProxy().getStackTraceElementProxyArray() : null));
			LogProcessor.storeSystemLog(entity);
		}

	}
}
