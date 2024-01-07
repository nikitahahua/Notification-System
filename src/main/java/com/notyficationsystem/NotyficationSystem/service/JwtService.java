package com.notyficationsystem.NotyficationSystem.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String jwt);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

}
