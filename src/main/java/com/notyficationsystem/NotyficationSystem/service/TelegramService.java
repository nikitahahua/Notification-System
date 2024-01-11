package com.notyficationsystem.NotyficationSystem.service;

import com.notyficationsystem.NotyficationSystem.model.TelegramContact;

public interface TelegramService {

    TelegramContact create(TelegramContact telegramContact);
    TelegramContact update(TelegramContact telegramContact);
    TelegramContact readByEmail(String email);
    TelegramContact readByName(String name);
    TelegramContact readByChatId(String chatId);
    void delete(TelegramContact telegramContact);

}
