package dev.mvc.team6_v2sbm3c;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  // topic으로 구독을 해놓은 유저에게 메세지를 보내줌
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");  // 특정 사용자에게 메시지 전송시 사용할 주소
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
        .addEndpoint("/ws")
        .setAllowedOrigins("http://localhost:9093")
        .withSockJS();
    }
}

