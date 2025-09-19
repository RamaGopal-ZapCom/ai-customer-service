package com.zapcom.repository;

import com.zapcom.entity.ChatbotMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;



public interface ChatbotMappingRepository extends MongoRepository<ChatbotMapping, String> {
    Optional<ChatbotMapping> findByApiKey(String apiKey);
    Optional<ChatbotMapping> findByCustomerEmail(String customerEmail);

}


