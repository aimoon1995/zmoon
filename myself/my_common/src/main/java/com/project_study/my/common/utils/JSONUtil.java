//package com.project_study.my.common.utils;
//
//import com.fasterxml.jackson.annotation.JsonFilter;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
//import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.AnnotationUtils;
//
//
///**
// *
// * com.ithinkdt.web.common.utils.JSONUtil.java
//
// * 此类继承ithinkdt-common的JacksonJsonUtil.java, 提供Java 对象转JSON字符串处理，使用了Spring框架中的util工具类。
// * @date 2018年9月21日
// * @version 1.0
// * @since JDK1.6
// */
//@Slf4j
//@SuppressWarnings("rawtypes")
//public class JSONUtil extends com.ithinkdt.common.utils.JacksonJsonUtil {
//	public static String toJsonStringInclude(Object object, Class cls, String... includeProps) {
//		try {
//			return getObMapperInstance()
//					.writer(new SimpleFilterProvider().addFilter(AnnotationUtils.getValue(AnnotationUtils.findAnnotation(cls, JsonFilter.class)).toString(),
//							SimpleBeanPropertyFilter.filterOutAllExcept(includeProps)))
//					.writeValueAsString(object);
//		} catch (JsonProcessingException e) {
//			return "";
//		}
//	}
//
//	public static String toJsonStringExclude(Object object, Class cls, String... excludeProps) {
//		try {
//			Object Annotation = AnnotationUtils.getValue(AnnotationUtils.findAnnotation(object.getClass(), JsonFilter.class));
//			return getObMapperInstance()
//					.writer(new SimpleFilterProvider().addFilter(StringUtil.getOrElse(Annotation), SimpleBeanPropertyFilter.serializeAllExcept(excludeProps)))
//					.writeValueAsString(object);
//		} catch (Exception e) {
//			log.error("json Serializer error", e);
//			return "";
//		}
//
//	}
//
//	public static String toJsonStringInclude(ObjectMapper obMap, Object object, Class cls, String... includeProps) {
//		try {
//			return obMap.writer(new SimpleFilterProvider().addFilter(
//					AnnotationUtils.getValue(AnnotationUtils.findAnnotation(object.getClass(), JsonFilter.class)).toString(),
//					SimpleBeanPropertyFilter.filterOutAllExcept(includeProps))).writeValueAsString(object);
//		} catch (JsonProcessingException e) {
//			return "";
//		}
//	}
//
//	public static String toJsonStringExclude(ObjectMapper obMap, Object object, Class cls, String... excludeProps) {
//		try {
//			return obMap.writer(new SimpleFilterProvider().addFilter(
//					AnnotationUtils.getValue(AnnotationUtils.findAnnotation(object.getClass(), JsonFilter.class)).toString(),
//					SimpleBeanPropertyFilter.serializeAllExcept(excludeProps))).writeValueAsString(object);
//		} catch (Exception e) {
//			return "";
//		}
//
//	}
//
//	public static String toJsonStringInclude(Object object, String... includeProps) {
//		if (object == null) {
//			return "";
//		}
//		return toJsonStringInclude(object, object.getClass(), includeProps);
//	}
//
//	public static String toJsonStringExclude(Object object, String... excludeProps) {
//		if (object == null) {
//			return "";
//		}
//		return toJsonStringExclude(object, object.getClass(), excludeProps);
//	}
//
//	public static String toJsonStringInclude(ObjectMapper obMap, Object object, String... includeProps) {
//		if (object == null) {
//			return "";
//		}
//		return toJsonStringInclude(obMap, object, object.getClass(), includeProps);
//	}
//
//	public static String toJsonStringExclude(ObjectMapper obMap, Object object, String... excludeProps) {
//		if (object == null) {
//			return "";
//		}
//		return toJsonStringExclude(obMap, object, object.getClass(), excludeProps);
//	}
//
//}
