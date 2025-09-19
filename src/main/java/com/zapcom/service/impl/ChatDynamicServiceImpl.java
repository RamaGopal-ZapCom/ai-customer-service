
package com.zapcom.service.impl;

import com.zapcom.config.ChatbotBaseUrlConfig;
import com.zapcom.entity.ChatbotMapping;
import com.zapcom.model.ChatRequest;
import com.zapcom.repository.ApiKeyRepository;
import com.zapcom.repository.ChatbotMappingRepository;
import com.zapcom.repository.JwtTokenRepository;
import com.zapcom.service.ChatDynamicService;
import com.zapcom.service.CustomerService;
import com.zapcom.util.TokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatDynamicServiceImpl implements ChatDynamicService {

    private static final Logger logger = LoggerFactory.getLogger(ChatDynamicServiceImpl.class);

    @Autowired
    private JwtTokenRepository jwtTokenRepository;
    private final ChatbotBaseUrlConfig chatbotBaseUrlConfig;
    private final ChatbotMappingRepository chatbotMappingRepository;
    private final RestTemplate restTemplate;
    private final ApiKeyRepository apiKeyRepository;
    private final CustomerService customerService;

    public ChatDynamicServiceImpl(
            ChatbotMappingRepository chatbotMappingRepository,
            RestTemplate restTemplate,
            ApiKeyRepository apiKeyRepository,
            ChatbotBaseUrlConfig chatbotBaseUrlConfig,
            CustomerService customerService
    ) {
        this.chatbotMappingRepository = chatbotMappingRepository;
        this.restTemplate = restTemplate;
        this.apiKeyRepository = apiKeyRepository;
        this.chatbotBaseUrlConfig = chatbotBaseUrlConfig;
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<?> checkHealth(HttpServletRequest request) {
        String url = getBaseUrlForApiKey(request) + "/health";
        return restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }

    @Override
    public ResponseEntity<?> sendMessage(HttpServletRequest request, ChatRequest requestBody) {
        String url = getBaseUrlForApiKey(request) + "/message";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ChatRequest> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    @Override
    public ResponseEntity<?> clearSession(HttpServletRequest request, String userId) {
        String url = getBaseUrlForApiKey(request) + "/SessionClear";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"user_id\":\"" + userId + "\"}", headers);

        return restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
    }

    @Override
    public ResponseEntity<?> getUsers(HttpServletRequest request) {
        String url = getBaseUrlForApiKey(request) + "/users";
        return restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }

@Override
public ResponseEntity<?> getStats(HttpServletRequest request) {
    String jwt;
    String apiKey;

    try {
        jwt = validateJwtToken(request);
        logger.info("Validated JWT token: {}", jwt);
    } catch (RuntimeException e) {
        logger.warn("JWT validation failed: {}", e.getMessage());
        return ResponseEntity.status(401).body(e.getMessage());
    }

    try {
        apiKey = TokenUtil.getCurrentApiKey(request);
        logger.info("Validated API Key: {}", apiKey);
    } catch (RuntimeException e) {
        logger.warn("API Key validation failed: {}", e.getMessage());
        return ResponseEntity.status(401).body(e.getMessage());
    }

    Optional<ChatbotMapping> mappingOptional = chatbotMappingRepository.findByApiKey(apiKey);
    if (mappingOptional.isEmpty()) {
        logger.warn("Invalid API Key in DB: {}", apiKey);
        return ResponseEntity.status(401).body("Invalid API key");
    }

    String url = getBaseUrlForApiKey(request) + "/stats/";
    logger.info("Fetching chatbot stats from URL: {}", url);

    HttpHeaders headers = new HttpHeaders();
    headers.set("api-key", apiKey);

    HttpEntity<Void> entity = new HttpEntity<>(headers);

    try {
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        logger.info("Successfully fetched chatbot stats.");
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    } catch (Exception e) {
        logger.error("Error fetching chatbot stats: {}", e.getMessage(), e);
        return ResponseEntity.status(500).body("Error fetching chatbot stats: " + e.getMessage());
    }
}

public String validateJwtToken(HttpServletRequest request) {
    String jwtHeader = request.getHeader("Authorization");

    if (jwtHeader != null && jwtHeader.startsWith("Bearer ")) {
        String jwt = jwtHeader.substring(7);
        logger.debug("Extracted JWT from header: {}", jwt);

        if (jwtTokenRepository.findByJwtToken(jwt).isPresent()) {
            return jwt;
        } else {
            logger.error("JWT token not found in DB: {}", jwt);
            throw new RuntimeException("JWT Token not found in DB: " + jwt);
        }
    }

    logger.error("JWT Token not found in header 'Authorization'");
    throw new RuntimeException("JWT Token not found in Header: " + jwtHeader);
}


    private String getBaseUrlForApiKey(HttpServletRequest request) {
        String apiKey = TokenUtil.getCurrentApiKey(request);
        Optional<ChatbotMapping> mappingOptional = chatbotMappingRepository.findByApiKey(apiKey);
        if (mappingOptional.isEmpty()) {
            throw new RuntimeException("Invalid API Key: " + apiKey);
        }
        String port = mappingOptional.get().getPort();
        if (port == null) {
            throw new RuntimeException("Port not found for API Key: " + apiKey);
        }
        return chatbotBaseUrlConfig.getBaseUrl() + ":" + port;
    }
}
