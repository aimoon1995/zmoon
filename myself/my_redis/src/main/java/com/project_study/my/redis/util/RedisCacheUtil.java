/**
 * Copyright (c),2016-2018, iThinkDT
 * <br/>This program is protected by copyright laws; 
 * <br/>Program Name: <b>ithinkdt-plugin-cache<b>
 * <br/>			 
 * Package: com.ithinkdt.redis.util <br/>
 * FileName: RedisCacheUtil.java <br/>
 *  <br/>
 * <br/> 
 * <br/>2018年10月22日
 * @author YangDong
 * @since JDK 1.8
 * @version 1.0
 */
package com.project_study.my.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * com.ithinkdt.redis.util.RedisCacheUtil.java
 * 
 * 提供缓存处理的最基本操作
 * 
 * @date 2018年10月22日
 * @author:YangDong
 * @version 1.0
 * @since JDK1.6
 */
@Component
public class RedisCacheUtil {

	@Autowired
	private RedisUtil redisUtil;

	private static RedisCacheUtil instance;

	public RedisCacheUtil() {
		instance = this;
	}

	/**
	 * 
	 * @author YangDong 2018年10月22日 下午11:23:53
	 * @param key
	 *            缓存key
	 * @return 获取key对应的value
	 */
	public static Object get(String key) {
		return instance.redisUtil.get(key);
	}

	public static boolean set(String key, Object value) {
		return instance.redisUtil.set(key, value);
	}

	/**
	 * 
	 * @author YangDong 2018年10月22日 下午11:25:23
	 * @param key
	 * @param value
	 * @param time
	 *            时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true:成功，false:失败
	 */
	public static boolean set(String key, Object value, long time) {
		return instance.redisUtil.set(key, value, time);
	}

	public boolean expire(String key, long time) {
		return instance.redisUtil.expire(key, time);
	}

	public void del(String... key) {
		instance.redisUtil.del(key);
	}
}
