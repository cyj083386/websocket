//package com.example.chatingdemo.configuration;
//
//import com.example.chatingdemo.handler.StompHandler;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//@RequiredArgsConstructor
//public class WebSocketConfig2 implements WebSocketMessageBrokerConfigurer {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger( WebSocketConfig.class );
//
//    // 세션 관리
//    final StompHandler stompHandler;
//
//    // 클라이언트가 웹 소켓 서버에 연결하는데 사용할 웹 소켓 엔드포인트 등록
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//
//        registry.addEndpoint("/ws")
//                .setAllowedOriginPatterns("*")
//                // client가 sockjs로 개발되어 있을 때만 필요, client가 java면 필요없음
//                .withSockJS()
//        ;
//    }
//
//    /*한 클라이언트에서 다른 클라이언트로 메시지를 라우팅하는데 사용될 메시지 브로커*/
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//
//        //sub으로 시작되는 요청을 구독한 모든 사용자들에게 메시지를 broadcast한다.
//        registry.enableSimpleBroker("/sub");
//
//        // pub로 시작되는 메시지는 message-handling methods로 라우팅된다.
//        registry.setApplicationDestinationPrefixes("/pub");
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        // connect / disconnect 인터셉터
//        registration.interceptors(stompHandler);
//    }
//}
