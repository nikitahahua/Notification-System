package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.MessageTemplate;
import com.notyficationsystem.NotyficationSystem.model.TelegramContact;
import com.notyficationsystem.NotyficationSystem.repository.TgRepo;
import com.notyficationsystem.NotyficationSystem.service.TelegramSenderService;
import com.notyficationsystem.NotyficationSystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class TelegramSenderServiceImpl implements TelegramSenderService {

    @Autowired
    private final TelegramLongPollingBot telegramBot;
    @Autowired
    private TgRepo repo;
    @Autowired
    private UserService userService;
    public TelegramSenderServiceImpl(TelegramLongPollingBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendEmail(String from, MessageTemplate text) {
        Set<TelegramContact> contacts = userService.getTelegramContactsByEmail(from);
        if (!contacts.isEmpty()) {
            for (TelegramContact contact : contacts) {
                Long chatId = contact.getChatId();
                SendMessage message = new SendMessage();
                message.setChatId(chatId.toString());
                String textExample = text.getTemplateText();
                textExample = textExample.replace("{{username}}", contact.getName());
                message.setText(textExample + "\n from : " + from);
                log.info("notifying: "+ contact.getName());
                try {
                    telegramBot.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
