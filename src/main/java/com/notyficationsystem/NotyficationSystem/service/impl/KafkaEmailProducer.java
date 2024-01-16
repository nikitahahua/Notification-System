package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.Contact;
import com.notyficationsystem.NotyficationSystem.model.Message;
import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.model.constant.MessageType;
import com.notyficationsystem.NotyficationSystem.service.KafkaDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class KafkaEmailProducer {

    private final KafkaDataService kafkaDataService;
    private final MessageTemplateServiceImpl messageTemplateService;
    public KafkaEmailProducer(KafkaDataService kafkaDataService, MessageTemplateServiceImpl messageTemplateService) {
        this.kafkaDataService = kafkaDataService;
        this.messageTemplateService = messageTemplateService;
    }

    public void notifyViaEmail(User contactsOwner, Integer id){

        Set<Contact> contacts = contactsOwner.getContacts();
        for (Contact contact:
                contacts) {
            if (Objects.equals(contactsOwner.getId(), contact.getUser().getId())){
                String textMessage = messageTemplateService.readById(Long.valueOf(id)).getTemplateText();
                String finalText = textMessage.replace("{{username}}", contactsOwner.getFullname());
                kafkaDataService.send(new Message(contact.getEmail(), contact.getContactName(), finalText, null, MessageType.EMAIL));
            }
            else{
                log.warn("this is not your contact "+contact.getEmail());
            }
        }
    }

}
