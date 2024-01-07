package com.notyficationsystem.NotyficationSystem.service;

public interface EmailSenderService {

    void sendEmail(String to, String subject, String text);

}
