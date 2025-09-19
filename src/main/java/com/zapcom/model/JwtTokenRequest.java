package com.zapcom.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JwtTokenRequest {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String customerEmail;

    @NotBlank(message = "JWT token cannot be blank")
    private String jwtToken;
}
