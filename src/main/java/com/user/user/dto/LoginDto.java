package com.user.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
    @NotEmpty(message = "Please provide your Email")
    @Email
    private String email;
    @NotEmpty(message = "Please provide your Password")
    private String password;
}
