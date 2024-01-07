package com.notyficationsystem.NotyficationSystem.service;

import com.notyficationsystem.NotyficationSystem.payload.LoginRequest;
import com.notyficationsystem.NotyficationSystem.payload.LoginResponse;
import com.notyficationsystem.NotyficationSystem.payload.RegisterRequest;
import com.notyficationsystem.NotyficationSystem.payload.RegisterResponse;

public interface AuthenticationService {

    RegisterResponse register(RegisterRequest registerRequest);

    void confirmEmail(String token);

    LoginResponse authenticate(LoginRequest loginRequest);

}