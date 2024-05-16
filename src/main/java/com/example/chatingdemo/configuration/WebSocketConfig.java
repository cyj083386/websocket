//package com.example.chatingdemo.configuration;
//
//import com.example.chatingdemo.handler.SocketHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//
//
//import lombok.extern.slf4j.Slf4j;
//
//@Configuration
//@EnableWebSocket
//@Slf4j
//public class WebSocketConfig implements WebSocketConfigurer {
//
//    @Autowired
//    SocketHandler socketHandler;
//
//    // 누군가 URL /chating --> socketHandler 발동
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        log.info("WebSocketConfig registerWebSocketHandlers Start!!");
//        registry.addHandler(socketHandler, "/chating");
//    }
//
//}