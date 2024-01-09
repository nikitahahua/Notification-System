package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.exceptions.InvalidConfirmationTokenException;
import com.notyficationsystem.NotyficationSystem.exceptions.UserNotFoundException;
import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.model.constant.Role;
import com.notyficationsystem.NotyficationSystem.payload.LoginRequest;
import com.notyficationsystem.NotyficationSystem.payload.LoginResponse;
import com.notyficationsystem.NotyficationSystem.payload.RegisterRequest;
import com.notyficationsystem.NotyficationSystem.payload.RegisterResponse;
import com.notyficationsystem.NotyficationSystem.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private JwtServiceImpl jwtService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ConfirmationTokenServiceImpl confirmationTokenService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        User user = new User();
        user.setFullname(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setEnabled(false);
        user.setRole(Role.USER);
        userService.create(user);
        String token = jwtService.generateToken(user);
        confirmationTokenService.sendConfirmationTokenToUser(user);
        return new RegisterResponse(token);
    }

    @Override
    public void confirmEmail(String token) {
        String username = jwtService.extractUsername(token);
        User user = (User) userDetailsService.loadUserByUsername(username);
        if (!jwtService.isTokenValid(token, user)) {
            throw new InvalidConfirmationTokenException();
        }
        userService.enableUser(user);
    }

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        User user = userService.readByEmail(loginRequest.username());
        if (!user.isEnabled()) {
            throw new UserNotFoundException();
        }
        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }
}
