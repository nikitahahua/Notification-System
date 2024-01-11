package com.notyficationsystem.NotyficationSystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "telegram_contacts")
@NoArgsConstructor
@Getter
@Setter
public class TelegramContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tg_chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "tg_user_id", nullable = false)
    private User user;

    public TelegramContact(Long chatId, String email) {
        this.chatId = chatId;
        this.email = email;
    }

}