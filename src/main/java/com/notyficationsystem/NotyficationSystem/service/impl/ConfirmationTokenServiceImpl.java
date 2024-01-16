package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.exceptions.ConfirmationTokenNotSendException;
import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.service.ConfirmationTokenService;
import com.notyficationsystem.NotyficationSystem.service.EmailService;
import com.notyficationsystem.NotyficationSystem.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtService jwtService;
    @Value("${network-address}")
    private String networkAddress;

    @Override
    public void sendConfirmationTokenToUser(User user) {
        String token = jwtService.generateToken(user);
        String to = user.getEmail();
        String subject = "Verify your account";
        String text = "Please click here to confirm your account \n"
                + networkAddress + "/auth/confirm-account?token=" + token;

        try {
            emailService.sendEmail(to, subject, text);
        } catch (MailSendException e) {
            throw new ConfirmationTokenNotSendException(Math.toIntExact(user.getId()));
        }
    }
}