package com.zapcom.config;

import com.zapcom.repository.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class ApiKeyHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        String requestUri = request.getRequestURI();

        if (requestUri.equals("/customers/createCustomerProfile") ||
                requestUri.equals("/customers/storeApiKey") ||
                requestUri.equals("/customers/storeJwtToken") ||
                requestUri.equals("/customers/getCustomer") ||
                requestUri.startsWith("/v3/api-docs") ||
                requestUri.startsWith("/swagger-ui") ||
                requestUri.equals("/swagger-ui.html")) {
            return true;
        }

        String apiKey = request.getHeader("api-key");
        if (apiKey == null || !apiKeyRepository.findByApiKey(apiKey).isPresent()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing API Key");
            return false;
        }

        return true;
    }
}
