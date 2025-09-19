package com.zapcom.controller;

import com.zapcom.model.ChatRequest;
import com.zapcom.service.ChatDynamicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Chat API", description = "Chatbot related endpoints")
@RestController
@RequestMapping("/customers/chatbot")
public class ChatDynamicController {

    private static final Logger logger = LoggerFactory.getLogger(ChatDynamicController.class);
    private final ChatDynamicService chatDynamicService;

    public ChatDynamicController(ChatDynamicService chatDynamicService) {
        this.chatDynamicService = chatDynamicService;
    }


    @Operation(summary = "Health Check Endpoint",
            security = {@SecurityRequirement(name = "api-key")})
    @GetMapping("/health")
    public ResponseEntity<?> checkHealth(HttpServletRequest request) {
        logger.info("Received Health Check request.");
        return chatDynamicService.checkHealth(request);
    }


    @Operation(summary = "Send message to chatbot",
            security = {@SecurityRequirement(name = "api-key")})
    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestBody ChatRequest requestBody, HttpServletRequest request) {
        logger.info("Received Send Message request.");
        return chatDynamicService.sendMessage(request, requestBody);
    }


    @Operation(summary = "Clear user session",
            security = {@SecurityRequirement(name = "api-key")})
    @DeleteMapping("/SessionClear")
    public ResponseEntity<?> clearSession(@RequestParam("user_id") String userId, HttpServletRequest request) {
        logger.info("Received Clear Session request for userId: {}", userId);
        return chatDynamicService.clearSession(request, userId);
    }


    @Operation(summary = "Get all chatbot users",
            security = {@SecurityRequirement(name = "api-key")})
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(HttpServletRequest request) {
        logger.info("Received Get Users request.");
        return chatDynamicService.getUsers(request);
    }


    @Operation(summary = "Get stats for the last 7 days",
            description = "Validates JWT Token and retrieves user stats.",
            security = {@SecurityRequirement(name = "jwt"), @SecurityRequirement(name = "api-key")})
    @GetMapping("/stats/")
    public ResponseEntity<?> getStats(HttpServletRequest request) {
        logger.info("Received Get Stats request.");
        return chatDynamicService.getStats(request);
    }
}
