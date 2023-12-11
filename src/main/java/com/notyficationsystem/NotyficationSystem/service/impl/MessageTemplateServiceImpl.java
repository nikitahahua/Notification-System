package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.MessageTemplate;
import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.repository.TemplateRepo;
import com.notyficationsystem.NotyficationSystem.service.MessageTemplateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {

    @Autowired
    private TemplateRepo templateRepo;

    @Override
    public MessageTemplate create(MessageTemplate message) {
        if (message != null){
            return templateRepo.save(message);
        }
        throw new EntityNotFoundException("cant find Person by email");
    }

    @Override
    public MessageTemplate update(MessageTemplate message) {
        MessageTemplate oldTemplate = null;
        if (templateRepo.findById(message.getTemplateId()).isPresent()){
            oldTemplate = templateRepo.findById(message.getTemplateId()).get();
            oldTemplate.setTemplateText(message.getTemplateText());
            return templateRepo.save(oldTemplate);
        }

        throw new EntityNotFoundException("cant find Template by id");
    }

    @Override
    public MessageTemplate readById(Long id) {
        return templateRepo.findById(id).orElseThrow(() ->
                new NoSuchElementException("wrong id was delivered"));
    }

    @Override
    public List<MessageTemplate> getAll() {
        return templateRepo.findAll();
    }

    @Override
    public List<MessageTemplate> getTemplatesByUser(User user) {
        return templateRepo.getMessageTemplateByUser(user);
    }

    @Override
    public void delete(Long id) {
        templateRepo.delete(readById(id));
    }
}
