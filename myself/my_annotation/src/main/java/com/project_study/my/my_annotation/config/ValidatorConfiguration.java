/**
 * Copyright (c),2016-2018, iThinkDT
 * <br/>This program is protected by copyright laws; 
 * <br/>Program Name: <b>ithinkdt-plugin-validator<b>
 * <br/>			 
 * Package: com.ithinkdt.web.validator <br/>
 * FileName: ValidatorConfiguration.java <br/>
 *  <br/>
 * <br/> 
 * <br/>2018年11月6日
 * @author YangDong
 * @since JDK 1.8
 * @version 1.0
 */
package com.project_study.my.my_annotation.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * com.ithinkdt.web.validator.ValidatorConfiguration.java
 * 
 * 
 * @date 2018年11月6日
 * @author:YangDong
 * @version 1.0
 * @since JDK1.8
 */
@Configuration
public class ValidatorConfiguration {

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor(LocalValidatorFactoryBean localValidatorFactoryBean) {
		MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
		processor.setValidator(localValidatorFactoryBean);
		return processor;
	}
	
	/*
	 * 1）定义Spring validator bean。
	 * Spring提供了两个Bean，LocalValidatorFactoryBean和CustomValidatorBean。
	 * LocalValidatorFactoryBean ：This is the central class for javax.validation
	 * (JSR-303) setup in a Spring application context: It bootstraps a
	 * javax.validation.ValidationFactory and exposes it through the Spring
	 * org.springframework.validation.Validator interface as well as through the
	 * JSR-303 javax.validation.Validator interface and the
	 * javax.validation.ValidatorFactory interface itself.
	 * LocalValidatorFactoryBean支持javax.validation.ValidationFactory接口，也支持javax.
	 * validation.Validator接口，由于它extends
	 * SpringValidatorAdapter，因此也支持org.springframework.validation.Validator接口，
	 * 属于N合一的Bean。 org.springframework.validation.Validator提供validate(Object
	 * target, org.springframework.validation.Errors
	 * errors)接口，将错误输出到Errors。下面是一个controller方法的例子，对页面的form的输入数据进行验证，如果错误，
	 * 存放到Errors中： public ModelAndView createEmployee(Map<String, Object>
	 * model,@Valid EmployeeForm form, Errors errors)
	 */
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		/*
		 * 1.1）自动寻找Validator实现。 LocalValidatorFactoryBean自动检查在classpath中的Bean
		 * Validation的实现，将javax.validation.ValidatorFactory作为其缺省备选，
		 * 本例将自动找到Hibernate Validator。但是如果在classpath下面有超过一个实现（例如运行在完全的J2EE
		 * web应用服务器，如GlassFish或WebSphere），这时通过下面方式指定采用哪个，以避免不可测性。
		 * validator.setProviderClass(HibernateValidator.class);
		 * 但这样的缺点在于是complie的而不是runtime的。要runtime，可以采用
		 * validator.setProviderClass(Class.forName(
		 * "org.hibernate.validator.HibernateValidator"));
		 * 但如果类写错了，无法在compile的时候查出
		 */
		// validator.setProviderClass(Class.forName("org.hibernate.validator.HibernateValidator"));
		validator.setProviderClass(HibernateValidator.class);
		/*
		 * 1.2）为Validator进行消息本地化
		 * 缺省的使用classpath路径下的ValidationMessages.properties,
		 * ValidationMessages_[language].properties,
		 * ValidationMessages_[language]_[region].properties），但在Bean
		 * validation1.1开始，可以自行提供国际化方式。
		 */
//		validator.setValidationMessageSource(messageSource);
		return validator;
	}

	/**
	 * 创建validator
	 * 
	 * @author YangDong 2018年11月6日 下午8:33:09
	 * @return Validator
	 */
	@Bean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(false)
				.messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("i18n/validation/messages"))).
				buildValidatorFactory();
		return validatorFactory.getValidator();
	}
}
