package com.notyficationsystem.NotyficationSystem.rest;

import com.notyficationsystem.NotyficationSystem.model.MessageTemplate;
import com.notyficationsystem.NotyficationSystem.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/")

public class TemplatesController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private MessageTemplateServiceImpl messageTemplateService;

    @PostMapping(value = "createPattern")
    public void createPattern(@RequestParam("pattern") String text, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        MessageTemplate messageTemplate = new MessageTemplate(text, userService.readByEmail(userDetails.getUsername()));
        messageTemplateService.create(messageTemplate);
        log.info("pattern created");
    }

    @GetMapping(value = "getAllPattens")
    public String  getAllPattens(){
        log.info("patterns returned");
        return messageTemplateService.getAll().toString();
    }

    @GetMapping("see-my-templates")
    public String seeMyTemplates(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("patterns returned");
        return messageTemplateService.getTemplatesByUser(userService.readByEmail(userDetails.getUsername())).toString();
    }
}
