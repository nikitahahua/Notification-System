package com.notyficationsystem.NotyficationSystem.rest;

import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.service.*;
import com.notyficationsystem.NotyficationSystem.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/")
public class NotifyController {
    private final UserService userService;
    private final KafkaEmailProducer emailProducer;
    private final KafkaTelegramProducer telegramProducer;
    private final CSVService CSVservice;
    private final MessageTemplateService templateService;
    public NotifyController(UserServiceImpl userService, CSVServiceImpl csVservice, MessageTemplateService templateService, KafkaEmailProducer emailProducer, KafkaTelegramProducer telegramProducer) {
        this.userService = userService;
        this.CSVservice = csVservice;
        this.templateService = templateService;
        this.emailProducer = emailProducer;
        this.telegramProducer = telegramProducer;
    }

    @PostMapping(value = "exportAll")
    public void importAll(@RequestParam("file") MultipartFile file, Authentication authentication){
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User contactUser = userService.readByEmail(userDetails.getUsername());
            CSVservice.importCSV(file.getInputStream(), contactUser);
        } catch (IOException e) {
            log.error("cant export contacts from file");
            throw new RuntimeException(e);
        }
        log.info("everyone is exported");

    }

    @GetMapping("email/sendNotification")
    public ResponseEntity<?> notifyEmail(@RequestParam("message_id") Integer id, Authentication authentication){
        log.info("ready to notify all contacts");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User contactUser = userService.readByEmail(userDetails.getUsername());
        emailProducer.notifyViaEmail(contactUser, id);
        return ResponseEntity.ok("Notification sent ");
    }

    @GetMapping("telegram/sendNotification")
    public ResponseEntity<?> sendNotification(@RequestParam("message_id") Integer id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User checkUser = userService.readByEmail(userDetails.getUsername());
        telegramProducer.sendMessageViaTelegram(checkUser.getUsername(), templateService.readById(Long.valueOf(id)));
        return ResponseEntity.ok("Notification sent ");
    }

    @GetMapping(value = "/notifyAll")
    public ResponseEntity<?> notifyAll(@RequestParam("message_id") Integer id, Authentication authentication) {
        log.info("Sending notifications to all contacts");

        notifyEmail(id, authentication);
        sendNotification(id, authentication);

        return ResponseEntity.ok("Notifications sent");
    }
}
