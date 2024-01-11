package com.notyficationsystem.NotyficationSystem.telegrambot.model;

import com.notyficationsystem.NotyficationSystem.telegrambot.constant.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {
    private String email;
    private UserState userState = UserState.NONE;
}
