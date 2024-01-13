package com.notyficationsystem.NotyficationSystem.repository;

import com.notyficationsystem.NotyficationSystem.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Long> {
    Contact findByEmail(String email);
    Contact findByContactName(String fullname);
    Contact findByChatId(Long chatId);
}