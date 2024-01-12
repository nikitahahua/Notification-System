package com.notyficationsystem.NotyficationSystem.repository;

import com.notyficationsystem.NotyficationSystem.model.Contact;
import com.notyficationsystem.NotyficationSystem.model.MessageTemplate;
import com.notyficationsystem.NotyficationSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepo extends JpaRepository<MessageTemplate, Long> {
    List<MessageTemplate> getMessageTemplateByUser(User user);
}