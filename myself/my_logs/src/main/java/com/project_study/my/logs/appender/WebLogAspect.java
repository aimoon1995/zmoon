//package com.project_study.my.logs.appender;
//
//import com.ithinkdt.common.utils.NumericUtil;
//import com.ithinkdt.common.utils.ReflectUtil;
//import com.ithinkdt.common.utils.StringUtil;
//import com.ithinkdt.web.WebContext;
//import com.ithinkdt.web.logs.constant.LogConstants;
//import com.ithinkdt.web.logs.entity.BusinessLogConfigEntity;
//import com.ithinkdt.web.logs.entity.BusinessLogEntity;
//import com.ithinkdt.web.logs.processor.LogProcessor;
//import com.ithinkdt.web.logs.service.LogService;
//import com.project_study.my.common.utils.StringUtil;
//import com.project_study.my.logs.entity.BusinessLogConfigEntity;
//import com.project_study.my.logs.entity.BusinessLogEntity;
//import com.project_study.my.logs.service.LogService;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static com.project_study.my.logs.constant.DbConstants.SQL_LOG_SEARCH_BUSINESS_LOG_CONFIG;
//import static com.project_study.my.logs.constant.LogConstants.*;
//
///**
// * 业务日志收集类
// */
//@Aspect
//@Component
//public class WebLogAspect implements LogConstants, InitializingBean {
//
//	private Map<String, BusinessLogConfigEntity> bizLogConfig;
//
//	@Autowired
//	LogService logService;
//
//	/**
//	 * 初始化
//	 * @throws Exception 异常
//	 */
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		initBizLogConfig();
//	}
//
//	/**
//	 * 重新载入配置
//	 */
//	public synchronized void initBizLogConfig() {
//		bizLogConfig = new HashMap<String, BusinessLogConfigEntity>();
//		List<BusinessLogConfigEntity> list = logService.search(null, SQL_LOG_SEARCH_BUSINESS_LOG_CONFIG);
//		for(BusinessLogConfigEntity ent : list) {
//			bizLogConfig.put(ent.getPointcut() + "." + ent.getClassName() + "." + ent.getMethodName(), ent);
//		}
//	}
//
//	/**
//	 * Controller切面
//	 */
//	@Pointcut("execution(* com.*.*.*.controller.*.*(..))")
//    public void controllerCall() {}
//
//	/**
//	 * Service切面
//	 */
//	@Pointcut("execution(* com.*.*.*.service.*.*(..))")
//    public void serviceCall() {}
//
//	/**
//	 * Dao切面
//	 */
//	@Pointcut("execution(* com.*.*.*.mapper.*.*(..)) ")
//    public void daoCall() {}
//
//	@Around(value="controllerCall()")
//	private Object controllerLog(ProceedingJoinPoint pjp) throws Throwable{
//		return log(pjp, LOG_BUSINESS_POINTCUT_CONTROLLER);
//	}
//
//	@Around(value="serviceCall()")
//	private Object serviceLog(ProceedingJoinPoint pjp) throws Throwable{
//		return log(pjp, LOG_BUSINESS_POINTCUT_SERVICE);
//	}
//
//	@Around(value="daoCall()")
//	private Object daoLog(ProceedingJoinPoint pjp) throws Throwable{
//		return log(pjp, LOG_BUSINESS_POINTCUT_DAO);
//	}
//
//	/**
//	 * 业务日志切面处理
//	 * @param pjp
//	 * @param pointcut
//	 * @return
//	 * @throws Throwable
//	 */
//	private Object log(ProceedingJoinPoint pjp, String pointcut) throws Throwable{
//		String className = pjp.getSignature().getDeclaringTypeName();
//		String methodName = pjp.getSignature().getName();
//		Object result = pjp.proceed();
//		recordLog(pointcut + "." + className + "." + methodName, pjp, result);
//		return result;
//	}
//
//	/**
//	 * 记录业务日志
//	 * @param key 配置KEY
//	 * @param pjp 切面
//	 * @param result 方法调用结果
//	 */
//	private void recordLog(String key, ProceedingJoinPoint pjp, Object result) {
//		if(bizLogConfig != null) {
//			BusinessLogConfigEntity config = bizLogConfig.get(key);
//			if (config != null) {
//				String msg = StringUtil.formatMessage(config.getMsg(), getParams(config.getParams(), pjp, result));
//				BusinessLogEntity entity = new BusinessLogEntity();
//				entity.setLogMessage(msg);
//				entity.setBusinessKey(key);
//				entity.setModuleName(config.getModule());
//				entity.setOperation(config.getTitle());
//				entity.setBusinessId(config.getId());
//				LogProcessor.storeBusinessLog(entity);
//			}
//		}
//	}
//
//	/**
//	 * 获取参数
//	 * @param params 参数表达式
//	 * @param pjp 切面
//	 * @param result 方法调用结果
//	 * @return 参数值
//	 */
//	private String[] getParams(String params, ProceedingJoinPoint pjp, Object result) {
//		String[] res = null;
//		if (StringUtil.isNotBlank(params)) {
//			String[] paramAry = params.split(";");
//			res = new String[paramAry.length];
//			for (int i = 0; i < paramAry.length; i++) {
//				String param = paramAry[i];
//				res[i] = getParam(param, pjp, result);
//			}
//		}
//		return res;
//	}
//
//	/**
//	 * 取单个参数
//	 * @param params 参数表达式
//	 * @param pjp 切面
//	 * @param result 方法调用结果
//	 * @return 参数值
//	 */
//	private String getParam(String param, ProceedingJoinPoint pjp, Object result) {
//		String[] paramParts = param.split("\\.");
//		String res = null;
//		if(paramParts != null && paramParts.length > 0) {
//			Object source = null;
//			switch (paramParts[0]) {
//				case "$SESSION":
//					source = WebContext.getSession();
//					break;
//				case "$WEBCONTEXT":
//					source = WebContext.get();
//					break;
//				case "$REQUEST":
//					source = WebContext.getRequest();
//					break;
//				case "$RESPONSE":
//					source = WebContext.getResponse();
//					break;
//				case "$PARAM":
//					source = pjp.getArgs();
//					break;
//				case "$RESULT":
//					source = result;
//					break;
//				default:
//			}
//			if (source != null && paramParts.length > 1) {
//				for (int i = 1; i < paramParts.length; i++) {
//					if(source != null) {
//						source = parseExpression(source, paramParts[i]);
//					} else {
//						break;
//					}
//				}
//			}
//			res = StringUtil.getOrElse(source);
//		}
//		return res;
//	}
//
//	/**
//	 * 解析参数
//	 * @param source 源
//	 * @param expression 表达式
//	 * @return 值
//	 */
//	private Object parseExpression(Object source, String expression) {
//		Object res = null;
//		if (source != null) {
//			if(source instanceof Map) {
//				res = ((Map) source).get(expression);
//			} else if (source instanceof Object[]) {
//				res = ((Object[]) source)[NumericUtil.toInteger(expression)];
//			} else if (source instanceof HttpSession) {
//				res = ((HttpSession) source).getAttribute(expression);
//			} else {
//				res = ReflectUtil.getPropertyValue(source, expression);
//			}
//		}
//		return res;
//	}
//}
