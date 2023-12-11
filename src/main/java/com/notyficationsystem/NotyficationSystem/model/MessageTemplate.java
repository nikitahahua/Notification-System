package com.notyficationsystem.NotyficationSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "message_templates")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "template_text")
    private String templateText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public MessageTemplate(String templateText, User user) {
        this.templateText = templateText;
        this.user = user;
    }

    public String getTemplateText() {
        return templateText;
    }
}