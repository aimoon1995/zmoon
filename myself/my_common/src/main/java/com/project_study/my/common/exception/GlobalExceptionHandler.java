package com.project_study.my.common.exception;


import com.project_study.my.common.ResultBean;
import com.project_study.my.common.enums.ErrorCodeEnums;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Set;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	
	/**
	 * 缺少必填参数
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultBean handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return ResultBean.result(false, "缺少必要参数[" + e.getParameterName() + "]");
    }

	/**
	 * HTTP方法不允许
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultBean handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResultBean.result(false, "不支持的HTTP方法");
    }

	/**
	 * 参数不匹配
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResultBean handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResultBean.result(false, "参数[" + e.getName() + "]的值应该为" + e.getRequiredType().getName() + "类型");
    }
	/**
	 * 参数验证未通过
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResultBean constraintViolationExceptionException(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		ConstraintViolation<?> violation = violations.iterator().next();
		String message = violation.getMessage();
		return ResultBean.result(false, "参数[" + violation.getPropertyPath().toString() + "]验证未通过：" + message);
	}

	/**
	 * 其他所有异常
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		// log.error(e.getMessage(), e);
		ResultBean error = ResultBean.result(false, "服务器发生了错误，请稍候重试！");
		log.error(e.getMessage(), e);
		// Expose ModelAndView for chosen error view.
		if (e instanceof BusinessException) {
			error.setCode(ErrorCodeEnums.SYSTEM_EXCEPTION);
			error.setMessages(e.getMessage());
			error.setStatus(false);
		} else if (e instanceof NoHandlerFoundException) {
			error.setCode(ErrorCodeEnums.Request_Not_Found);
			//error.setMessages(MessageUtil.getMessage(MsgNames.MSG_E_SYSTEM_NO_HANDLER_FOUND));
			error.setMessages("请求的资源不存在，请确认请求地址是否正确");
			error.setData(request.getRequestURI());
			error.setStatus(false);	
		}
//		String callback = StringUtil.getOrElse(WebContext.getParameter("jsonpcallbackfunction"), (String)WebContext.getParameter("JSON_CALLBACK"));
//		if (StringUtils.isNotEmpty(callback)) {
//			JSONPObject jsonp = new JSONPObject(callback, error);
//			try {
//				ObjectMapper objectMapper = new ObjectMapper();
//				response.getOutputStream().write(objectMapper.writeValueAsString(jsonp).getBytes());
//			} catch (IOException e1) {
//				log.error(e1.getMessage(), e1);
//			}
//			return null;
//		}
		
		return error;
	}
	

}