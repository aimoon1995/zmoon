/**
 * Copyright (c),2016-2018, iThinkDT
 * <br/>This program is protected by copyright laws;
 * <br/>Program Name: nio-dqa<b>ithinkdt<b>
 * <br/>
 * Package: com.ithinkdt.dqa.bom.compare.vehiclecostverify.service <br <br/>
 * FileName: CalcService<br <br/>
 * <br/>
 * <br/>
 * <br/>
 *
 * @author ithinkdt
 * @version 1.0
 * @since JDK 1.8
 */
package com.project_study.my.for_my.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * * com.ithinkdt.dqa.bom.compare.vehiclecostverify.service
 *
 * @version 1.0
 * @descreption:
 * @date:2019/01/14 15:28
 * @author: liubo
 * @since JDK1.8
 */
@Service
@Slf4j
public class CalcService implements MessageListener {


    @Autowired
    RedisTemplate redisTemplate;



    @Override
    public void onMessage(Message message, byte[] pattern) {
    	String key = null;
        try {
            log.info("接收到消息---------------------------");

        } catch (Exception e) {

        } finally {


        }
    }
}