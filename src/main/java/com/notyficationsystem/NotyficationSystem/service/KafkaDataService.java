package com.notyficationsystem.NotyficationSystem.service;

import com.notyficationsystem.NotyficationSystem.model.Message;

public interface KafkaDataService {
    void send(Message message);
}
