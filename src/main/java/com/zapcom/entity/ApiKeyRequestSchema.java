package com.zapcom.entity;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ApiKeyRequestSchema {
    private String apiKey;
    private String customerEmail;
}

