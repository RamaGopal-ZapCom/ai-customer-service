package com.zapcom.config;

import com.zapcom.repository.JwtTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class JwtHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    //
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String requestUri = request.getRequestURI();


        if (requestUri.contains("/customers/getCustomer") || requestUri.contains("/customers/chatbot/stats/")) {

            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
                return false;
            }

            String jwtToken = authHeader.substring(7).trim();


            boolean tokenExists = jwtTokenRepository.findByJwtToken(jwtToken).isPresent();

            if (!tokenExists) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token");
                return false;
            }

            return true;
        }

        return true;
    }




}
