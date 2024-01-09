package com.notyficationsystem.NotyficationSystem.rest;

import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.service.MessageTemplateService;
import com.notyficationsystem.NotyficationSystem.service.TelegramSenderService;
import com.notyficationsystem.NotyficationSystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/telegram")
@Slf4j
public class TelegramNotificationController {

    private final TelegramSenderService telegramSenderService;
    private final UserService userService;
    private final MessageTemplateService templateService;

    @Autowired
    public TelegramNotificationController(TelegramSenderService telegramSenderService, UserService userService, MessageTemplateService templateService) {
        this.telegramSenderService = telegramSenderService;
        this.userService = userService;
        this.templateService = templateService;
    }

    @PostMapping("/sendNotification")
    public ResponseEntity<?> sendNotification(Authentication authentication) {
        log.info("got into controller");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User checkUser = userService.readByEmail(userDetails.getUsername());
        telegramSenderService.sendEmail(checkUser.getUsername(), templateService.getAll().get(0));
        return ResponseEntity.ok("Notification sent ");
    }


}
