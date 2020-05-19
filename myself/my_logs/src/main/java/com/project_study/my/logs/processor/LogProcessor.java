package com.project_study.my.logs.processor;


import com.project_study.my.common.utils.DateUtil;
import com.project_study.my.common.utils.ServerTools;
import com.project_study.my.common.utils.StringUtil;
import com.project_study.my.common.utils.UUIDGenerator;
import com.project_study.my.logs.constant.LogConstants;
import com.project_study.my.logs.entity.BusinessLogEntity;
import com.project_study.my.logs.entity.SystemLogEntity;
import com.project_study.my.logs.service.LogStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 系统日志处理类
 * @author 浩
 *
 */
@Component
@Slf4j
public class LogProcessor implements LogConstants {

	/**
	 * 日志持久化服务
	 */
	@Resource
	private LogStorageService logService;
	
	private static LogProcessor instance;
	
	public LogProcessor(){
		instance = this;
	}

	/**
	 * 检测是持久化服务是否已初始化
	 * @return 是否已初始化
	 */
	private static boolean loaded(){
		return instance != null && instance.logService != null;
	}

	
	/**
	 * 记录系统日志
	 * @param sysLog 系统日志
	 */
	public static void storeSystemLog(SystemLogEntity sysLog){
		log.info("+++++++++++======++++++++++=====))))))))))");
		if(loaded()){

			//记录操作人日志
            sysLog.setId(UUIDGenerator.get());
			//String logUser = WebContext.getSessionUserId();
			sysLog.setLogUser("system");

			// 记录时间
			sysLog.setLogTime(new Date());
			// 客户端IP
	//		sysLog.setClientIp(WebContext.getClientIP());
			// 服务端IP
            sysLog.setServerIp(ServerTools.getServerIP());

			instance.logService.doSystemLog(sysLog);
			// 发送系统通知
		//	sendSystemErrorLogNotice(sysLog);
		}
	}



	

	
}
