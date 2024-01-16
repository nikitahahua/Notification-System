package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.Message;
import com.notyficationsystem.NotyficationSystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
public class KafkaTelegramConsumer {
    private final TelegramLongPollingBot telegramBot;

    public KafkaTelegramConsumer(TelegramLongPollingBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @KafkaListener(
            topics = "telegram-messages",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void receiveMessageFromKafka(@Payload Message message) {
        Long chatId = message.getChatID();
        if (chatId == null) {
            log.warn(message.getMessageType() + " this contact does not subscribe in telegram !");
            return;
        }

        SendMessage telegramMessage = new SendMessage();
        telegramMessage.setChatId(chatId.toString());
        telegramMessage.setText(message.getText());

        try {
            telegramBot.execute(telegramMessage);
            log.info("Notified: " + message.getEmail());
        } catch (TelegramApiException e) {
            log.error("Failed to send message to Telegram: " + e.getMessage(), e);
        }
    }

}
