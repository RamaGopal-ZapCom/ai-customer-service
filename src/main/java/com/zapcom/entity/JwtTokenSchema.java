package com.zapcom.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "jwt_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class JwtTokenSchema {
    @Id
    private String customerEmail;
    private String jwtToken;
}
