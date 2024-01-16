package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.Message;
import com.notyficationsystem.NotyficationSystem.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaEmailConsumer {

    private final EmailService emailService;

    public KafkaEmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "email-messages",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    private void sendEmail(Message message) {
        emailService.sendEmail(message.getEmail(), "Notification System Made By Hahua Inc.", message.getText());
    }

}
