package com.project_study.my.my_annotation;

import com.project_study.my.common.ResultBean;
import com.project_study.my.common.enums.ErrorCodeEnums;
import com.project_study.my.common.param.BaseParam;
import com.project_study.my.my_annotation.annotation.CustomValidate;
import com.project_study.my.common.entity.ValidatorMessages;
import io.swagger.annotations.ApiModelProperty;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Component
@Aspect
public class ParamValidate {
	/**
	 * 引入validate校验，此引入的validate校验结果返回第一条错误信息，默认为false(显示全部错误信息)，true显示一条错误信息 
	 */
	@Autowired
	private Validator validator;
    private static final String  MSG_PARAMETER_VALIDATION_FAILED = "Parameter validation failed.";
    private static final MethodValidationPostProcessor methodProcessor = new MethodValidationPostProcessor();
    //定义校验的包位置
    @Pointcut("@annotation(com.project_study.my.my_annotation.annotation.CustomValidate))")
    public void pointcut() {
    }

    /**
     * 入参校验
     * 1.参数验证分组，区分同一个参数对象的不同方法，例如，新增、编辑.同一个参数对象：新增需要check帐号， 编辑不需要check
     * 2.定义消息ID，错误消息根据ID获取消息内容，支持国际化
     * 3.定义是否一次性显示全部错误消息，在CustomValidate中定义属性，默认提示一个错误
     * 4.多条错误消息以列表形式返回，放在ResultBean的data中，前端根据code判断，格式化消息进行显示
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
    	//存放错误消息的list列表
        List<ValidatorMessages> messageList = new ArrayList<ValidatorMessages>();
        //创建resultBean并赋值
        ResultBean<List<ValidatorMessages>> resultBean = new ResultBean<List<ValidatorMessages>>();
    	try {
    		methodProcessor.setValidator(validator);
    		//获取目标方法体参数
            Object[] args = joinPoint.getArgs();
            //获取目标方法签名
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            //获取目标方法
            Method targetMethod = methodSignature.getMethod();
            //通过反射获取类声明的方法
            Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());
            //获取类中方法上指定的CustomValidate注解
            Annotation annotation = targetMethod.getAnnotation(CustomValidate.class);
            //获取CustomValidate注解中的groups值
            Class<?>[] customValue = ((CustomValidate) annotation).groups();
            //获取CustomValidate注解中的是否显示全部错误信息的isShowAll值，false为显示一条错误信息，true为显示全部错误信息
            boolean isShowAll = ((CustomValidate) annotation).showAll();
            resultBean.setStatus(false);
            resultBean.setCode(ErrorCodeEnums.PARAM_VALIDATION_ERROR);
            if (args != null) {
            	for (Object arg : args) {
            		if (arg != null) {
            			//分组校验
            			Set<ConstraintViolation<Object>> constraintViolations = validator.validate(arg, customValue);
            			//如果存在错误信息
            			if (constraintViolations.size() > 0) {
            				for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            					//创建错误信息实体类并将错误信息及错误字段放入该实体类中
            					ValidatorMessages validateMessages = new ValidatorMessages();
            					//将消息放入msg字段
            					String filedName = constraintViolation.getPropertyPath().toString();
            					//获取属性的注解
            					Annotation customValueType = constraintViolation.getConstraintDescriptor().getAnnotation();
            					//根据属性中类的不同注解返回不同的信息
            					annotationType(customValueType, constraintViolation, validateMessages);
            					//校验出错的字段
            					validateMessages.setFieldName(filedName);
            					Field f = arg.getClass().getDeclaredField(filedName);
            					if (f != null) {
            						ApiModelProperty apiAnnotation = f.getAnnotation(ApiModelProperty.class);
            						if (apiAnnotation != null) {
            							validateMessages.setFieldLabel(apiAnnotation.value());
            						}
            					}
            					messageList.add(validateMessages);
            					if (!isShowAll) {
            						break;
            					}
            				}
            			}
            		}
            	}
            }
    	} catch (Exception e) {
			e.printStackTrace();
			ResultBean<?> error = new ResultBean<>();
			error.setStatus(false);
			error.setMessages(MSG_PARAMETER_VALIDATION_FAILED);
			return error;
		}
        if (messageList.size() == 0) {
            //将请求转交给相应的控制器处理
            return joinPoint.proceed();
        }
        resultBean.setMessages(MSG_PARAMETER_VALIDATION_FAILED);
        resultBean.setData(messageList);
        return resultBean;
    }

    /**
     * 根据属性中类的不同注解返回不同的信息
     * @param customValueType 属性的注解
     * @param constraintViolation 校验相关信息
     * @param validateMessages 返回的错误信息实体类
     * @return void
     */
    public void annotationType(Annotation customValueType, ConstraintViolation<Object> constraintViolation, ValidatorMessages validateMessages) {
        //获取属性中注解的值
        Map<String, Object> constraintDescriptor = constraintViolation.getConstraintDescriptor().getAttributes();
        //错误提示消息
        String messageTemp = constraintViolation.getMessageTemplate();
        //如果注解是Length，Size，Range
        if (customValueType instanceof Length || customValueType instanceof Size || customValueType instanceof Range) {
            String min = constraintDescriptor.get("min").toString();
            String max = constraintDescriptor.get("max").toString();
            validateMessages.setMsg(this.getMsgs(messageTemp, min, max));
            //如果注解是Max
        } else if (customValueType instanceof Max) {
            String max = constraintDescriptor.get("value").toString();
            validateMessages.setMsg(this.getMsgs(messageTemp, max));
            //如果注解是Min
        } else if (customValueType instanceof Min) {
            String min = constraintDescriptor.get("value").toString();
            validateMessages.setMsg(this.getMsgs(messageTemp, min));
            //如果注解是DecimalMin
        } else if (customValueType instanceof DecimalMin) {
            String value = constraintDescriptor.get("value").toString();
            validateMessages.setMsg(this.getMsgs(messageTemp, value));
            //如果注解是DecimalMax
        } else if (customValueType instanceof DecimalMax) {
            String value = constraintDescriptor.get("value").toString();
            validateMessages.setMsg(this.getMsgs(messageTemp, value));
        } else {
            validateMessages.setMsg(this.getMsgs(messageTemp));
        }
    }
    
    /**
     * 根据消息编码获取消息内容
     * @author YangDong 2018年11月6日 下午1:40:30
     * @param code 消息资源编码
     * @param args 消息内容参数
     * @return 实际消息内容
     */
    private String getMsgs(String code, String... args) {
    	return code;
    }

}