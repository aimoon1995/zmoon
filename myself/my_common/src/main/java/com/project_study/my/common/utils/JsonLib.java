//package com.project_study.quartz.common.utils;
//
//import net.sf.json.JSON;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import net.sf.json.JsonConfig;
//import net.sf.json.util.CycleDetectionStrategy;
//import net.sf.json.util.JSONUtils;
//
//import java.util.Date;
//
//public class JsonLib {
//
//	public static String toJson(Object obj){
//		return toJson(obj,new String[]{});
//	}
//
//	public static String toJson(String dateTimePattern,Object obj){
//		return toJson(dateTimePattern,obj,new String[]{});
//	}
//
//	public static String toJson(Object obj,String... excludePropertyNames){
//		return toJson(null,obj,excludePropertyNames);
//	}
//
//	public static String toJson(String dateTimePattern,Object obj,String... excludePropertyNames){
//
//		if(obj == null){
//			return null;
//		}
//
//		String realDateTimePattern=DateUtil.DATE_FORMAT_DATETIME_M_HMS;
//		if(!StringUtil.isEmpty(dateTimePattern)){
//			realDateTimePattern=dateTimePattern;
//		}
//
//		JsonConfig config = new JsonConfig();
//		config.setIgnoreDefaultExcludes(false);
//		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
//		config.registerJsonValueProcessor(Date.class,
//				new DateJsonValueProcessor(realDateTimePattern));
//
//		String[] excludes=new String[excludePropertyNames.length+2];
//		excludes[0]="handler";
//		excludes[1]="hibernateLazyInitializer";
//		System.arraycopy(excludePropertyNames, 0, excludes, 2, excludePropertyNames.length);
//		config.setExcludes(excludes);
//
//		JSON json = null;
//		if(JSONUtils.isArray(obj)){
//			json = JSONArray.fromObject(obj,config);
//		}else{
//			json = JSONObject.fromObject(obj,config);
//		}
//
//		return json != null ? json.toString():null;
//	}
//
//}
