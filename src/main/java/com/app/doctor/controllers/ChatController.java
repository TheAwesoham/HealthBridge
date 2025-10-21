package com.app.doctor.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.app.doctor.entity.Chat;

@Controller
public class ChatController {
    
    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public Chat sendMessage(@Payload Chat chatMessage) {
        return chatMessage;
    }
}
