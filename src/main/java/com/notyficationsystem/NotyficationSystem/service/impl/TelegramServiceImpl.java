package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.TelegramContact;
import com.notyficationsystem.NotyficationSystem.repository.TgRepo;
import com.notyficationsystem.NotyficationSystem.service.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramServiceImpl implements TelegramService {

    private final TgRepo tgRepo;

    public TelegramServiceImpl(TgRepo tgRepo) {
        this.tgRepo = tgRepo;
    }

    @Override
    public TelegramContact create(TelegramContact telegramContact) {
        return tgRepo.save(telegramContact);
    }

    @Override
    public TelegramContact update(TelegramContact telegramContact) {
        return tgRepo.save(telegramContact);
    }

    @Override
    public TelegramContact readByEmail(String email) {
        return tgRepo.findByEmail(email);
    }

    @Override
    public TelegramContact readByName(String name) {
        return tgRepo.findByName(name);
    }

    @Override
    public TelegramContact readByChatId(String chatId) {
        return tgRepo.findByChatId(Long.valueOf(chatId));
    }

    @Override
    public void delete(TelegramContact telegramContact) {
        tgRepo.delete(telegramContact);
    }
}