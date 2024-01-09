package com.notyficationsystem.NotyficationSystem.telegrambot.model;

import com.notyficationsystem.NotyficationSystem.telegrambot.constant.UserState;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserStateCache {
    private final Map<Long, UserSession> userStates = new ConcurrentHashMap<>();

    public void setUserSession(long userId, UserSession session) {
        userStates.put(userId, session);
    }

    public UserSession getUserSession(long userId) {
        return userStates.get(userId);
    }
}