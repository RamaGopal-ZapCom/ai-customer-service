
package com.zapcom.util;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    public static String getCurrentApiKey(HttpServletRequest request) {
        String apiKey = request.getHeader("api-key");

        if (apiKey != null && !apiKey.isEmpty()) {
            logger.debug("Extracted API Key from header: {}", apiKey);
            return apiKey;
        }

        logger.error("API Key not found in header 'api-key'");
        throw new RuntimeException("API Key not found in Header:"+apiKey);
    }

    public static String extractTokenFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header format");
        }
        return authHeader.substring(7);
    }
}
