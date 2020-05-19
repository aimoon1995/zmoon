//package com.project_study.my.common.utils;
//
//import com.thoughtworks.xstream.XStream;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.beanutils.BeanMap;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map.Entry;
//
//@Slf4j
//public class Serializer {
//
//	public static String toXml(Object entity){
//		String result = "";
//		try{
//			if(entity != null){
//				 XStream xstream = new XStream();
//				result = xstream.toXML(entity);
//			}
//		}catch(Exception ex){
//			log.error("toXml(Object entity) Exception", ex);
//		}
//		return result;
//	}
//
//	public static String toXml(Object entity, String... excludes){
//		String result = "";
//		try{
//			if(entity != null){
//				 XStream xstream = new XStream();
//				 if(excludes != null){
//					 Class<?> c = entity.getClass();
//					for(String exclude:excludes){
//						if(StringUtil.isNotEmpty(exclude)){
//							xstream.omitField(c, exclude);
//						}
//					}
//				}
//				result = xstream.toXML(entity);
//			}
//		}catch(Exception ex){
//			log.error("toXml(Object entity, String... excludes) Exception", ex);
//		}
//		return result;
//	}
//
//	public static String toStringIncludeProperties(Object entity, boolean ignoreNull, String... includes){
//		String result = "";
//		if(entity != null){
//			BeanMap map = new BeanMap(entity);
//			Iterator<Entry<Object, Object>> iterator = map.entryIterator();
//			StringBuffer buffer = new StringBuffer();
//			List<String> includeList = null;
//			if(includes != null){
//				includeList = Arrays.asList(includes);
//			}
//			buffer.append("Class").append(":").append(entity.getClass().getName());
//			while(iterator.hasNext()){
//				Entry<Object, Object> entry = iterator.next();
//				String key = StringUtil.getOrElse(entry.getKey());
//				String value = StringUtil.getOrElse(entry.getValue());
//				if(ignoreNull && StringUtil.isEmpty(value)){
//					continue;
//				}
//				if(includeList != null && includeList.contains(key) && !"class".equalsIgnoreCase(key)){
//					buffer.append("\r\n");
//					buffer.append(key).append(":").append(value);
//				}
//			}
//			result = buffer.toString();
//		}
//		return result;
//	}
//
//	public static String toString(Object entity, boolean ignoreNull, String... extExcludes){
//		String result = "";
//		if(entity != null){
//			BeanMap map = new BeanMap(entity);
//			Iterator<Entry<Object, Object>> iterator = map.entryIterator();
//			StringBuffer buffer = new StringBuffer();
//			List<String> excludeList = new ArrayList<String>();
//			excludeList.add("rows");
//			excludeList.add("pageNo");
//			excludeList.add("backPage");
//			excludeList.add("sortName");
//			excludeList.add("sortOrder");
//			excludeList.add("createDate");
//			excludeList.add("createUser");
//			excludeList.add("updateDate");
//			excludeList.add("updateUser");
//			excludeList.add("liveIndt");
//
//			if(extExcludes != null){
//				for(String ext:extExcludes){
//					if(!excludeList.contains(ext)){
//						excludeList.add(ext);
//					}
//				}
//			}
//			buffer.append("Class").append(":").append(entity.getClass().getName());
//			while(iterator.hasNext()){
//				Entry<Object, Object> entry = iterator.next();
//				String key = StringUtil.getOrElse(entry.getKey());
//				String value = StringUtil.getOrElse(entry.getValue());
//				if(ignoreNull && StringUtil.isEmpty(value)){
//					continue;
//				}
//				if("class".equalsIgnoreCase(key) || (excludeList != null && excludeList.contains(key))){
//					continue;
//				}
//				buffer.append("\r\n");
//				buffer.append(key).append(":").append(value);
//			}
//			result = buffer.toString();
//		}
//		return result;
//	}
//}
