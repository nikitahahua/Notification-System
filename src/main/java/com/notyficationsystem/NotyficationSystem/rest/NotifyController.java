package com.notyficationsystem.NotyficationSystem.rest;

import com.notyficationsystem.NotyficationSystem.model.Contact;
import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ContactServiceImpl contactService;
    @Autowired
    private EmailSenderServiceImpl emailSenderService;
    @Autowired
    private CSVServiceImpl CSVservice;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping(value = "exportAll")
    public void importAll(@RequestParam("file") MultipartFile file){
        try {
            CSVservice.importCSV(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("GOT HERE!! FINALLY!!");

    }

    @GetMapping(value = "notifyAll")
    @PreAuthorize("isAuthenticated()")
    public void notifyAll(@RequestParam("text") String text, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("ready to notify all contacts");
        List<Contact> people = contactService.getAll();

        for (Contact contact:
                people) {
            if (Objects.equals(userService.readByEmail(userDetails.getUsername()).getId(), contact.getUser().getId())){
                emailSenderService.sendEmail(contact.getEmail(),"Emergency Notify ",text);
            }
            else{
                log.warn("this is not your contact "+contact.getEmail());
            }
        }
    }

    @GetMapping("/")
    public void test1(){
        log.info("ready to notify all contacts");
        List<Contact> people = contactService.getAll();

        for (Contact contact:
                people) {
            emailSenderService.sendEmail(contact.getEmail(),"Emergency Notify ", "test1");
        }
    }

    @GetMapping("/sex")
    public String test(){
        List<User> list =
        userService.getAll();
        return "fuck pinguins";
    }


}
