package com.Java.hotelmanagementsystem.email.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @NotBlank(message = "Token cannot be empty")
    private String token;

    @NotBlank(message = "Password cannot be empty")
    private String password;
}