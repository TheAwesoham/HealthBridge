package com.app.doctor.entity;

import lombok.Data;

@Data
public class Chat {
    
    private String roomId;
    private String sender;
    private String content;
}
