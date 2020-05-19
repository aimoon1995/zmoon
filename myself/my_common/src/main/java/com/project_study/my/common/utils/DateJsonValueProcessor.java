//package com.project_study.quartz.common.utils;
//
//import net.sf.json.JsonConfig;
//import net.sf.json.processors.JsonValueProcessor;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class DateJsonValueProcessor implements JsonValueProcessor {
//	private DateFormat dateFormat;
//
//	public DateJsonValueProcessor(String datePattern) {
//		if (datePattern == null) {
//			dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATE_M_1);
//		} else {
//			dateFormat = new SimpleDateFormat(datePattern);
//		}
//
//	}
//
//	@Override
//	public Object processArrayValue(Object arg0, JsonConfig arg1) {
//
//		return process(arg0);
//	}
//
//	@Override
//	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
//		return process(arg1);
//	}
//
//	private Object process(Object value){
//		if (value == null) {
//			return "";
//		} else {
////			return dateFormat.format((Date) value);
//			return ((Date) value).getTime();
//		}
//	}
//
//	/**
//	 * @return the dateFormat
//	 */
//	public DateFormat getDateFormat() {
//		return dateFormat;
//	}
//
//	/**
//	 * @param dateFormat the dateFormat to set
//	 */
//	public void setDateFormat(DateFormat dateFormat) {
//		this.dateFormat = dateFormat;
//	}
//
//}
