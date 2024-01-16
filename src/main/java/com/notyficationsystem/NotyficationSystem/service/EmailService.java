package com.notyficationsystem.NotyficationSystem.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
