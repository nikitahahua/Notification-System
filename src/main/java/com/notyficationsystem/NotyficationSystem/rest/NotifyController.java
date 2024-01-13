package com.notyficationsystem.NotyficationSystem.rest;

import com.notyficationsystem.NotyficationSystem.model.Contact;
import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/")
public class NotifyController {
    private final UserServiceImpl userService;
    private final ContactServiceImpl contactService;
    private final EmailSenderServiceImpl emailSenderService;
    private final CSVServiceImpl CSVservice;
    private final MessageTemplateServiceImpl messageTemplateService;

    public NotifyController(UserServiceImpl userService, ContactServiceImpl contactService, EmailSenderServiceImpl emailSenderService, CSVServiceImpl csVservice, MessageTemplateServiceImpl messageTemplateService) {
        this.userService = userService;
        this.contactService = contactService;
        this.emailSenderService = emailSenderService;
        CSVservice = csVservice;
        this.messageTemplateService = messageTemplateService;
    }

    @PostMapping(value = "exportAll")
    public void importAll(@RequestParam("file") MultipartFile file, Authentication authentication){
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User contactUser = userService.readByEmail(userDetails.getUsername());
            CSVservice.importCSV(file.getInputStream(), contactUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("everyone is notified");

    }

    @GetMapping(value = "notifyAll")
    public void notifyAll(@RequestParam("message_id") Integer id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("ready to notify all contacts");
        List<Contact> people = contactService.getAll();

        for (Contact contact:
                people) {
            User checkUser = userService.readByEmail(userDetails.getUsername());
            if (Objects.equals(checkUser.getId(), contact.getUser().getId())){
                String textMessage = messageTemplateService.readById(Long.valueOf(id)).getTemplateText();
                String finalText = textMessage.replace("{{username}}", checkUser.getFullname());
                emailSenderService.sendEmail(contact.getEmail(),"Emergency Notify ", finalText);
            }
            else{
                log.warn("this is not your contact "+contact.getEmail());
            }
        }
    }

}
