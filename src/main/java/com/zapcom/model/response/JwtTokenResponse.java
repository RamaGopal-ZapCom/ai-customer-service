package com.zapcom.model.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class JwtTokenResponse {
    private String customerEmail;
    private String jwtToken;
}
