package com.notyficationsystem.NotyficationSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "contacts")
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phoneNumber", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    public Contact(User user, String contactName, String email, String phoneNumber, Long chatId) {
        this.user = user;
        this.contactName = contactName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.chatId = chatId;
    }

}

