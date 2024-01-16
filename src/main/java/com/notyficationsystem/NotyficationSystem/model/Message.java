package com.notyficationsystem.NotyficationSystem.model;

import com.notyficationsystem.NotyficationSystem.model.constant.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String email;
    private String name;
    private String text;
    private Long chatID;
    private MessageType messageType;
}
