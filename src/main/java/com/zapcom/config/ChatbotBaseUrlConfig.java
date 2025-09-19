package com.zapcom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatbotBaseUrlConfig {

    @Value("${chatbot.base-url}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }
}

