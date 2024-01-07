package com.notyficationsystem.NotyficationSystem.service;

import com.notyficationsystem.NotyficationSystem.model.User;

public interface ConfirmationTokenService {

    void sendConfirmationTokenToUser(User user);

}
