package com.notyficationsystem.NotyficationSystem.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class MailParams {

    private Integer id;
    private String mailTo;
    private String subject;
    private String text;

}
