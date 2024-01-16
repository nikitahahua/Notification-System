package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.Contact;
import com.notyficationsystem.NotyficationSystem.model.Message;
import com.notyficationsystem.NotyficationSystem.model.MessageTemplate;
import com.notyficationsystem.NotyficationSystem.model.constant.MessageType;
import com.notyficationsystem.NotyficationSystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class KafkaTelegramProducer {
    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final UserService userService;

    public KafkaTelegramProducer(KafkaTemplate<String, Message> kafkaTemplate, UserService userService) {
        this.kafkaTemplate = kafkaTemplate;
        this.userService = userService;
    }

    public void sendMessageViaTelegram(String from, MessageTemplate text) {
        Set<Contact> contacts = userService.getContactsByEmail(from);
        if (!contacts.isEmpty()) {
            for (Contact contact : contacts) {
                String textExample = text.getTemplateText();
                textExample = textExample.replace("{{username}}", contact.getContactName());
                kafkaTemplate.send("telegram-messages", new Message(from, contact.getContactName(), textExample, contact.getChatId(), MessageType.TELEGRAM));
                log.info("Sent message to Kafka topic: " + text + " from: " + from + " to contact: " + contact.getContactName());
            }
        }
    }
}
