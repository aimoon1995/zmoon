///**
// * Copyright (c),2016-2018, iThinkDT
// * <br/>This program is protected by copyright laws;
// * <br/>Program Name: nio-dqa<b>ithinkdt<b>
// * <br/>
// * Package: com.ithinkdt.dqa.bom.compare.config <br <br/>
// * FileName: WebSocketNettyConfig<br <br/>
// * <br/>
// * <br/>
// * <br/>
// *
// * @author ithinkdt
// * @version 1.0
// * @since JDK 1.8
// */
//package com.project_study.my.for_my.config.websocket;
//
//import java.util.Map;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import com.ithinkdt.web.common.utils.JSONUtil;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// *  * com.ithinkdt.dqa.bom.compare.config
// * @descreption:
// * @date:2019/01/14 18:23
// * @author: liubo
// * @version 1.0
// * @since JDK1.8
// */
////@Configuration
////@EnableWebSocket
//@Slf4j
//public class WebSocketNettyConfig implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        // 注册自定义消息处理，消息路径为`/ws/foo`
//        registry.addHandler(new CalcWebSocketHandler(),"/pub/socket").
//				addInterceptors(new HandshakeInterceptor() {
//					@Override
//					public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
//							Map<String, Object> attributes) throws Exception {
//						ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//						log.info("beforeHandshake : " + JSONUtil.toJsonString(servletRequest.getServletRequest().getParameterMap()));
//						return true;
//					}
//					@Override
//					public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//						ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//						log.info("afterHandshake : " + JSONUtil.toJsonString(servletRequest.getServletRequest().getParameterMap()));
//					}
//				}).
//        setAllowedOrigins("*");
//    }
//}
