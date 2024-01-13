package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.Contact;
import com.notyficationsystem.NotyficationSystem.model.MessageTemplate;
import com.notyficationsystem.NotyficationSystem.service.TelegramSenderService;
import com.notyficationsystem.NotyficationSystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Set;

@Service
@Slf4j
public class TelegramSenderServiceImpl implements TelegramSenderService {

    private final TelegramLongPollingBot telegramBot;
    private final UserService userService;
    public TelegramSenderServiceImpl(TelegramLongPollingBot telegramBot, UserService userService) {
        this.telegramBot = telegramBot;
        this.userService = userService;
    }

    @Override
    public void sendEmail(String from, MessageTemplate text) {
        Set<Contact> contacts = userService.getContactsByEmail(from);
        if (!contacts.isEmpty()) {
            for (Contact contact : contacts) {
                Long chatId = contact.getChatId();
                if (chatId == null){
                    log.warn(contact.getEmail()+ " this contact do not subscribed in telegram !");
                    continue;
                }
                SendMessage message = new SendMessage();
                message.setChatId(chatId.toString());
                String textExample = text.getTemplateText();
                textExample = textExample.replace("{{username}}", contact.getContactName());
                message.setText(textExample + "\n from : " + from);
                log.info("notifying: "+ contact.getContactName());
                try {
                    telegramBot.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
