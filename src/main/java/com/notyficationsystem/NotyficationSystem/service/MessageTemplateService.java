package com.notyficationsystem.NotyficationSystem.service;

import com.notyficationsystem.NotyficationSystem.model.MessageTemplate;
import com.notyficationsystem.NotyficationSystem.model.User;

import java.util.List;

public interface MessageTemplateService {

    MessageTemplate create(MessageTemplate message);
    MessageTemplate update(MessageTemplate message);
    MessageTemplate readById(Long id);
    List<MessageTemplate> getAll();
    List<MessageTemplate> getTemplatesByUser(User user);
    void delete(Long id);

}
