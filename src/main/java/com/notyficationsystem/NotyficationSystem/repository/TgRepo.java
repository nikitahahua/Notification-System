package com.notyficationsystem.NotyficationSystem.repository;

import com.notyficationsystem.NotyficationSystem.model.TelegramContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TgRepo extends JpaRepository<TelegramContact, Long> {
    List<TelegramContact> findByTelegramUsername(String telegramUsername);
    TelegramContact findByChatId(Long chatId);
}
