/**
 * Copyright (c),2016-2018, iThinkDT
 * <br/>This program is protected by copyright laws;
 * <br/>Program Name: nio-dqa<b>ithinkdt<b>
 * <br/>
 * Package: com.ithinkdt.dqa.bom.compare.config <br <br/>
 * FileName: RedisQueueConf<br <br/>
 * <br/>
 * <br/>
 * <br/>
 *
 * @author ithinkdt
 * @version 1.0
 * @since JDK 1.8
 */
package com.project_study.my.for_my.config.redisCnf;


import com.project_study.my.for_my.constant.RedisQueueConstant;
import com.project_study.my.for_my.service.CalcService;
import com.project_study.my.for_my.service.QueueNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * * com.ithinkdt.dqa.bom.compare.config
 *
 * @version 1.0
 * @descreption:
 * @date:2019/01/14 15:24
 * @author: liubo
 * @since JDK1.8
 */
@Configuration
public class RedisQueueConf {

    @Bean
    RedisMessageListenerContainer container(
            @Autowired RedisConnectionFactory redisConnectionFactory,
            @Qualifier("listenerAdapter") MessageListenerAdapter listenerAdapter,
            @Qualifier("notifyAdapter") MessageListenerAdapter notifyAdapter
    ) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(RedisQueueConstant.CALC_CHANNEL));
        container.addMessageListener(notifyAdapter, new PatternTopic(RedisQueueConstant.CALC_NOTIFY_CHANNEL));
        return container;
    }

    @Bean(name = "listenerAdapter")
    MessageListenerAdapter listenerAdapter(CalcService consumer) {
        return new MessageListenerAdapter(consumer, "onMessage");
    }

    @Bean(name = "notifyAdapter")
    MessageListenerAdapter notifyAdapter(QueueNotifyService consumer) {
        return new MessageListenerAdapter(consumer, "onMessage");
    }
}
