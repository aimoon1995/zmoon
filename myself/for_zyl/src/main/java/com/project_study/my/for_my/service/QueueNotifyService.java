/**
 * Copyright (c),2016-2018, iThinkDT
 * <br/>This program is protected by copyright laws;
 * <br/>Program Name: nio-dqa<b>ithinkdt<b>
 * <br/>
 * Package: com.ithinkdt.dqa.bom.compare.vehiclecostverify.service <br <br/>
 * FileName: QueueNotifyService<br <br/>
 * <br/>
 * <br/>
 * <br/>
 *
 * @author ithinkdt
 * @version 1.0
 * @since JDK 1.8
 */
package com.project_study.my.for_my.service;


import com.project_study.my.common.utils.JacksonJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.channels.Channel;

/**
 *  * com.ithinkdt.dqa.bom.compare.vehiclecostverify.service
 * @descreption:
 * @date:2019/01/15 17:25
 * @author: liubo
 * @version 1.0
 * @since JDK1.8
 */
@Service
@Slf4j
public class QueueNotifyService implements MessageListener {


    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            log.info("接收到消息---------------------------");

//            Object text = redisTemplate.getValueSerializer().deserialize(message.getBody());
//            for (Channel channel : WebSocketServerHandler.channels) {
//                channel.writeAndFlush(new TextWebSocketFrame(JacksonJsonUtil.toJsonString(text)));
//            }
//            //TODO 通知消息
//            for (WebSocketSession session : CalcWebSocketHandler.allSessions()) {
//                try {
//                    Object text = redisTemplate.getValueSerializer().deserialize(message.getBody());
//                    if(text instanceof VehicleMessParam ){
//                       VehicleMessParam param=(VehicleMessParam)text;
//                       if("0".equals(param.getIfExit())) {
//                           Thread.sleep(1000);
//                       }
//                }
//                    if (session.isOpen()) {
//                        session.sendMessage(new TextMessage(JacksonJsonUtil.toJsonString(text)));
//                    }
//                } catch (Exception e) {
//                    log.error("接收队列消息发生异常,", e);
//                }
//            }
        } catch (Exception e) {
            log.warn("接收消息异常，", e);
        }

    }
}