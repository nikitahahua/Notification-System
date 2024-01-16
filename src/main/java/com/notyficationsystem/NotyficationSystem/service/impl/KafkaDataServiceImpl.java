package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.Message;
import com.notyficationsystem.NotyficationSystem.service.KafkaDataService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaDataServiceImpl implements KafkaDataService {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public KafkaDataServiceImpl(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(Message message) {
        String topic = switch (message.getMessageType()){
            case TELEGRAM -> "telegram-messages";
            case EMAIL -> "email-messages";
        };
        kafkaTemplate.send(topic, message);
    }
}
