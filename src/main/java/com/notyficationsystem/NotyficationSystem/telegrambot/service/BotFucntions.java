package com.notyficationsystem.NotyficationSystem.telegrambot.service;

public interface BotFucntions {
    void notifyUsers(long chatId);
    void addTemplate(long chatId);
    void subscribeUser(long chatId);
    void sendDefaultMessage(long chatId);
}
