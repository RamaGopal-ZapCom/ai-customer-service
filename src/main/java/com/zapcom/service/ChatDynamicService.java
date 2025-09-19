
package com.zapcom.service;

import com.zapcom.model.ChatRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ChatDynamicService {
    ResponseEntity<?> checkHealth(HttpServletRequest request);
    ResponseEntity<?> sendMessage(HttpServletRequest request, ChatRequest requestBody);
    ResponseEntity<?> clearSession(HttpServletRequest request, String userId);
    ResponseEntity<?> getUsers(HttpServletRequest request);
    ResponseEntity<?> getStats(HttpServletRequest request);
    String validateJwtToken(HttpServletRequest request);
}