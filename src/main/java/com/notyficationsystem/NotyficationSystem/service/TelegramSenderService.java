package com.notyficationsystem.NotyficationSystem.service;

import com.notyficationsystem.NotyficationSystem.model.MessageTemplate;

public interface TelegramSenderService {

    void sendEmail(String to, MessageTemplate text);

}
