package com.project_study.my.for_my.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName WebSocketNettyConfig
 * @Description: 编写一个WebSocketConfig配置类，注入对象ServerEndpointExporter，
 *   *      这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
 * @Author zyl
 * @Date 2020/1/14
 * @Version V1.0
 **/
@Configuration
public class WebSocketConfig {
           @Bean
           public ServerEndpointExporter serverEndpointExporter() {
           return new ServerEndpointExporter();
           }
}