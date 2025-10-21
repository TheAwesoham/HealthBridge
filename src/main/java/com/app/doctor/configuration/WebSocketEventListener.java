package com.app.doctor.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //enables WebSocket message handling using STOMP protocol
public class WebSocketEventListener implements WebSocketMessageBrokerConfigurer{
    //configures the message broker — defines how messages are routed between client and server
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); //simple in-memory message broker for broadcasting
        config.setApplicationDestinationPrefixes("/app"); //for messages sent from client to server
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //defines the WebSocket connection endpoint at "/ws-chat"
        //clients connect to this endpoint to initiate a WebSocket session
        registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*").withSockJS();
    }
}

//message flow -> 

//client connects to the WebSocket server - ws://localhost:8080/ws-chat
//"client" refers to the frontend application that connects through SockJS and STOMP

//client sends message to -> /app/chat.sendMessage/{roomId} handled by a @MessageMapping method in your controller

//server returns the response which gets published to -> /topic/chat/{roomId}
//all the subscribed clients to -> /topic/chat/{roomId} will get the message broadcasted 