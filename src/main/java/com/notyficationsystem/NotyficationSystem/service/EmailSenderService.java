package com.notyficationsystem.NotyficationSystem.service;

import com.notyficationsystem.NotyficationSystem.dto.MailParams;

public interface EmailSenderService {

    void sendEmail(String to, String subject, String text);

}
