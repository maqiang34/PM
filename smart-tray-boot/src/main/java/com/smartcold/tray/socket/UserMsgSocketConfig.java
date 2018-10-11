package com.smartcold.tray.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Copyright (C) SmartCold 版权所有
 *
 * @author MaQiang34
 * @Description: 基类
 * @createDate:2018-09-13 17:43
 * @version:V1.0
 */
//
//@Configuration
//@EnableWebSocketMessageBroker
public class UserMsgSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new MyWebSocketHandler(), "/websocket").addInterceptors(new WebSocketInterceptor());
    }
}
