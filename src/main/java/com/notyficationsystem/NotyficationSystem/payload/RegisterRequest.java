package com.notyficationsystem.NotyficationSystem.payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest (
        @NotBlank(message = "Username is mandatory")
        String username,

        @Email(message = "Email is not valid")
        @NotBlank(message = "Email is mandatory")
        String email,

        @NotBlank(message = "Password is mandatory")
        String password
) {

}