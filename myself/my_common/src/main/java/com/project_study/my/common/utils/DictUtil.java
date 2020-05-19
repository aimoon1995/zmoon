//package com.project_study.my.common.utils;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//@Component
//@Slf4j
//public class DictUtil {
//
//	@Autowired
//	private RedisUtil redisUtil;
//
//	private static DictUtil instance;
//
//	public DictUtil() {
//		instance = this;
//	}
//
//	@SuppressWarnings("unchecked")
//	private Map<String, List<Code>> getCodeMap() {
//		return (Map<String, List<Code>>) redisUtil.get(Constants.ITHINKDT_WEB_DICT_CACHE);
//	}
//
//	public static String getMultiCode(String key, String codes, String splitor) {
//		return getMultiCode(key, codes, splitor, getCodeMap(key));
//	}
//
//	public static String getMultiCode(String key, String codes) {
//		return getMultiCode(key, codes, ",");
//	}
//
//	private static String getMultiCode(String key, String codes, String splitor, Map<String, String> map) {
//		if (map != null && map.size() > 0 && StringUtil.isNotEmpty(codes)) {
//			String[] values = StringUtil.split(codes, splitor);
//			StringBuffer sb = new StringBuffer();
//			for (String value : values) {
//				String text = StringUtil.getOrElse(map.get(value));
//				if (StringUtil.isNotEmpty(text)) {
//					if (sb.length() > 0) {
//						sb.append(splitor);
//					}
//					sb.append(text);
//				}
//			}
//			return sb.toString();
//		}
//		return "";
//	}
//
//	public static Map<String, String> getCodeMap(String key, String locale) {
//		List<Code> list = getCodeList(key);
//		Map<String, String> map = new HashMap<String, String>();
//		for (Code code : list) {
//			if (DictAnnotation.LOCALE_ZH_CN.equalsIgnoreCase(locale)) {
//				map.put(code.value, code.text);
//			} else if (DictAnnotation.LOCALE_EN.equalsIgnoreCase(locale)) {
//				map.put(code.value, code.enText);
//			}
//		}
//		return map;
//	}
//
//	public static Map<String, String> getCodeMap(String key) {
//		return getCodeMap(key, DictAnnotation.LOCALE_ZH_CN);
//	}
//
//	public static List<Code> getCodeList(String key, String... excludes) {
//		List<Code> list = new CopyOnWriteArrayList<Code>();
//		List<Code> all = null;
//		if (instance != null) {
//			Map<String, List<Code>> codeMap = instance.getCodeMap();
//			if (codeMap != null) {
//				all = codeMap.get(key);
//			}
//		}
//		if (all != null) {
//			// Collections.sort(all); 字典数据已经有排序，该排序操作在高并发下影响效率，取消 2018-09-18
//			// 11:16:14
//			if (excludes != null && excludes.length > 0) {
//				List<String> elist = Arrays.asList(excludes);
//				int i = all.size() - 1;
//				for (; i > -1; i--) {
//					Code code = all.get(i);
//					if (!elist.contains(code.value)) {
//						list.add(code);
//					}
//				}
//			} else {
//				return all;
//			}
//		}
//		return list;
//	}
//
//	/**
//	 *
//	 * 将描述字段值从字典中取值
//	 *
//	 * @author YangDong 2017年8月17日 下午2:01:38
//	 * @param t
//	 *            Entity
//	 * @param <T>
//	 *            数据类型
//	 * @return T
//	 */
//	@SuppressWarnings({ "rawtypes" })
//	public static <T> T assignDesc(T t) {
//		if (t == null) {
//			return t;
//		}
//		try {
//			Class<?> objclass = t.getClass();
//			Field[] at = ReflectUtil.getDeclaredFields(objclass).toArray(new Field[] {});
//			for (Field fd : at) {
//				boolean isPrimitive = fd.getType().isPrimitive();
//				fd.setAccessible(true);
//				Object fieldVal = null;
//				if (!isPrimitive) {
//					fieldVal = fd.get(t);
//				}
//				if (!isPrimitive && fd.getType().getName().startsWith("com.ithinkdt") && fd.getType().getName().indexOf("$") < 0) {
//					// 非原生类型且属于com.ithinkdt package 时需要再做一次assign，此判断条件属于临时方案
//					assignDesc(fd.get(t));
//				} else if (!isPrimitive && fieldVal != null && fieldVal instanceof List && ((List) fieldVal).size() > 0) {
//					// assignDesc((List)fieldVal);
//				} else if (fd.isAnnotationPresent(DictAnnotation.class)) {
//					DictAnnotation d = fd.getAnnotation(DictAnnotation.class);
//					if (d != null) {
//						Field codeField;
//						codeField = ReflectUtil.getDeclaredField(objclass, d.valueField());
//						String code = "";
//						if (codeField != null) {
//							codeField.setAccessible(true);
//							Object temp = codeField.get(t);
//							if (temp != null) {
//								code = temp.toString();
//							}
//						}
//						if (StringUtil.isNotEmpty(d.defaultText()) && StringUtil.isEmpty(code)) {
//							fd.set(t, d.defaultText());
//							continue;
//						}
//						String val = getDictValue(d.dictType(), code, StringUtil.getOrElse(d.locale(), WebContext.getLocale()));
//						// 从字典中获取DESC
//						fd.set(t, val);
//					}
//				}
//			}
//		} catch (SecurityException e) {
//			log.info("assignDesc error, SecurityException", e);
//		} catch (IllegalArgumentException e) {
//			log.info("assignDesc error, IllegalArgumentException", e);
//		} catch (IllegalAccessException e) {
//			log.info("assignDesc error, IllegalAccessException", e);
//		} catch (Error e) {
//			log.info("Unkonw Error", e);
//		}
//		return t;
//	}
//
//	/**
//	 * 从字典中获取对应字典值的中英文名称 Array[0]：中文描述 Array[1]：英文描述
//	 *
//	 * @author YangDong 2017年8月17日 下午2:22:35
//	 * @param type
//	 *            字典分类
//	 * @param code
//	 *            字典值
//	 * @param locale
//	 *            中文\英文
//	 * @return 字典中文描述和英文描述
//	 */
//	public static String getDictValue(String type, String code, String locale) {
//		Map<String, String> codeMap = DictUtil.getCodeMap(type, locale);
//		if (codeMap != null) {
//			return codeMap.get(code);
//		}
//		return null;
//	}
//
//	/**
//	 * 获取中文描述
//	 *
//	 * @author YangDong 2017年8月17日 下午2:27:14
//	 * @param type
//	 *            字典分类
//	 * @param code
//	 *            字典值
//	 * @return dictValue
//	 */
//	public static String getDictChValue(String type, String code) {
//		return getDictValue(type, code, DictAnnotation.LOCALE_ZH_CN);
//	}
//
//	/**
//	 * 获取英文描述
//	 *
//	 * @author YangDong 2017年8月17日 下午2:27:26
//	 * @param type
//	 *            字典分类
//	 * @param code
//	 *            字典值
//	 * @return dictValue
//	 */
//	public static String getDictEnValue(String type, String code) {
//		return getDictValue(type, code, DictAnnotation.LOCALE_EN);
//	}
//
//	/**
//	 *
//	 *
//	 * @author YangDong 2017年8月17日 下午1:58:17
//	 * @param page
//	 *            分页信息
//	 * @param <T>
//	 *            数据类型
//	 * @return T
//	 */
//	public static <T> Page<T> assignDesc(Page<T> page) {
//		assignDesc(page.getResult());
//		return page;
//	}
//
//	/**
//	 *
//	 *
//	 * @author YangDong 2017年8月17日 下午1:58:22
//	 * @param list
//	 *            list
//	 * @param <T>
//	 *            数据类型
//	 * @return T
//	 */
//	public static <T> List<T> assignDesc(List<T> list) {
//		for (T t : list) {
//			assignDesc(t);
//		}
//		return list;
//	}
//}
