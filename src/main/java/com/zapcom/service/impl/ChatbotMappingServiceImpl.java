////
////package com.zapcom.service.impl;
////
////import com.zapcom.entity.ChatbotMapping;
////import com.zapcom.repository.ChatbotMappingRepository;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////
////import java.util.Optional;
////
////@Service
////public class ChatbotMappingServiceImpl {
////
////    private static final Logger logger = LoggerFactory.getLogger(ChatbotMappingServiceImpl.class);
////
////    @Autowired
////    private ChatbotMappingRepository chatbotMappingRepository;
////
//////    public void saveMapping(String apiKey, String port) {
//////        logger.info("Saving ChatbotMapping with API Key: {} and Port: {}", apiKey, port);
//////
//////        ChatbotMapping mapping = new ChatbotMapping();
//////        mapping.setApiKey(apiKey);
//////        mapping.setPort(port);
//////        chatbotMappingRepository.save(mapping);
//////
//////        logger.info("ChatbotMapping saved successfully for API Key: {}", apiKey);
//////public void saveMapping(String apiKey, String port) {
//////    logger.info("Saving/Updating ChatbotMapping with API Key: {} and Port: {}", apiKey, port);
//////
//////    Optional<ChatbotMapping> existingMappingOpt = chatbotMappingRepository.findByApiKey(apiKey);
//////
//////    ChatbotMapping mapping;
//////    if (existingMappingOpt.isPresent()) {
//////        mapping = existingMappingOpt.get();
//////        mapping.setPort(port); // Update port
//////        logger.info("Updated existing ChatbotMapping for API Key: {}", apiKey);
//////    } else {
//////        mapping = new ChatbotMapping();
//////        mapping.setApiKey(apiKey);
//////        mapping.setPort(port);
//////        logger.info("Created new ChatbotMapping for API Key: {}", apiKey);
//////    }
//////
//////    chatbotMappingRepository.save(mapping);
//////}
//////public void saveMapping(String customerEmail, String apiKey, String port) {
//////    logger.info("Saving/Updating ChatbotMapping with CustomerEmail: {}, API Key: {}, Port: {}", customerEmail, apiKey, port);
//////
//////    Optional<ChatbotMapping> existingMappingOpt = chatbotMappingRepository.findByCustomerEmail(customerEmail);
//////
//////    ChatbotMapping mapping;
//////    if (existingMappingOpt.isPresent()) {
//////        mapping = existingMappingOpt.get();
//////        mapping.setApiKey(apiKey);  // update API key
//////        // keep existing port if no new port provided
//////        if (port != null && !port.isEmpty()) {
//////            mapping.setPort(port);
//////        }
//////        logger.info("Updated existing ChatbotMapping for CustomerEmail: {}", customerEmail);
//////    } else {
//////        mapping = ChatbotMapping.builder()
//////                .customerEmail(customerEmail)
//////                .apiKey(apiKey)
//////                .port(port)
//////                .build();
//////        logger.info("Created new ChatbotMapping for CustomerEmail: {}", customerEmail);
//////    }
//////
//////    chatbotMappingRepository.save(mapping);
//////}
//////public void saveMapping(String customerEmail, String apiKey, String port) {
//////    logger.info("Saving/Updating ChatbotMapping with CustomerEmail: {}, API Key: {}, Port: {}", customerEmail, apiKey, port);
//////
//////    Optional<ChatbotMapping> existingMappingOpt = chatbotMappingRepository.findByCustomerEmail(customerEmail);
//////
//////    ChatbotMapping mapping;
//////    if (existingMappingOpt.isPresent()) {
//////        mapping = existingMappingOpt.get();
//////        mapping.setApiKey(apiKey);  // Update API key
//////
//////        if (port != null && !port.trim().isEmpty()) {
//////            mapping.setPort(port); // Only update if a valid new port is provided
//////        } else {
//////            logger.info("No new port provided, retaining existing port: {}", mapping.getPort());
//////        }
//////
//////        logger.info("Updated existing ChatbotMapping for CustomerEmail: {}", customerEmail);
//////    } else {
//////        mapping = ChatbotMapping.builder()
//////                .customerEmail(customerEmail)
//////                .apiKey(apiKey)
//////                .port(port) // will be null if not provided, which is okay for initial creation
//////                .build();
//////        logger.info("Created new ChatbotMapping for CustomerEmail: {}", customerEmail);
//////    }
//////
//////    chatbotMappingRepository.save(mapping);
//////}
//////public void saveMapping(String customerEmail, String apiKey, String port) {
//////    logger.info("Saving/Updating ChatbotMapping with CustomerEmail: {}, API Key: {}, Port: {}", customerEmail, apiKey, port);
//////
//////    Optional<ChatbotMapping> existingMappingOpt = chatbotMappingRepository.findByCustomerEmail(customerEmail);
//////
//////    ChatbotMapping mapping;
//////    if (existingMappingOpt.isPresent()) {
//////        mapping = existingMappingOpt.get();
//////        mapping.setApiKey(apiKey);  // Update API key
//////
//////        if (port != null && !port.trim().isEmpty()) {
//////            mapping.setPort(port);  // Update port if explicitly provided
//////            logger.info("Updated port to new value: {}", port);
//////        } else {
//////            String existingPort = mapping.getPort(); // fetch explicitly
//////            mapping.setPort(existingPort);           // explicitly re-set it
//////            logger.info("No new port provided, retaining existing port: {}", existingPort);
//////        }
//////
//////        logger.info("Updated existing ChatbotMapping for CustomerEmail: {}", customerEmail);
//////    } else {
//////        mapping = ChatbotMapping.builder()
//////                .customerEmail(customerEmail)
//////                .apiKey(apiKey)
//////                .port(port) // may be null for first creation
//////                .build();
//////        logger.info("Created new ChatbotMapping for CustomerEmail: {}", customerEmail);
//////    }
//////
//////    chatbotMappingRepository.save(mapping);
//////}
////public void saveMapping(String customerEmail, String apiKey) {
////    logger.info("Attempting to update ChatbotMapping for CustomerEmail: {} with API Key: {}", customerEmail, apiKey);
////
////    Optional<ChatbotMapping> existingMappingOpt = chatbotMappingRepository.findByCustomerEmail(customerEmail);
////
////    if (existingMappingOpt.isPresent()) {
////        ChatbotMapping mapping = existingMappingOpt.get();
////        mapping.setApiKey(apiKey); // update only the API key
////        // Do NOT touch mapping.setPort(...) so existing port is retained
////        chatbotMappingRepository.save(mapping);
////        logger.info("Updated API Key for existing ChatbotMapping. Port retained: {}", mapping.getPort());
////    } else {
////        logger.info("No existing ChatbotMapping found for CustomerEmail: {}. Skipping creation as per requirement.", customerEmail);
////    }
////}
////
////}
////
//package com.zapcom.service.impl;
//
//import com.zapcom.entity.ChatbotMapping;
//import com.zapcom.repository.ChatbotMappingRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class ChatbotMappingServiceImpl {
//
//    private static final Logger logger = LoggerFactory.getLogger(ChatbotMappingServiceImpl.class);
//
//    @Autowired
//    private ChatbotMappingRepository chatbotMappingRepository;
//
//    /**
//     * Updates the API key for the existing ChatbotMapping of the customer.
//     * If the mapping does not exist, does nothing (no insertion).
//     * Retains the existing manually set port during API key reset.
//     *
//     * @param customerEmail The customer's email (used as unique identifier)
//     * @param apiKey        The new API key to set
//     */
////    public void saveMapping(String customerEmail, String apiKey) {
////        logger.info("Attempting to update ChatbotMapping for CustomerEmail: {} with API Key: {}", customerEmail, apiKey);
////
////        Optional<ChatbotMapping> existingMappingOpt = chatbotMappingRepository.findByCustomerEmail(customerEmail);
////
////        if (existingMappingOpt.isPresent()) {
////            ChatbotMapping mapping = existingMappingOpt.get(); // fully populated, including manually set port
////
////            mapping.setApiKey(apiKey); // update only API key, retain existing port
////
////            chatbotMappingRepository.save(mapping);
////            logger.info("Successfully updated API Key for CustomerEmail: {}. Port retained: {}", customerEmail, mapping.getPort());
////        } else {
////            logger.info("No existing ChatbotMapping found for CustomerEmail: {}. Skipping update, as creation is not required.", customerEmail);
////        }
////    }
//    public void saveMapping(String customerEmail, String apiKey) {
//        logger.info("Updating ChatbotMapping for CustomerEmail: {} with API Key: {}", customerEmail, apiKey);
//
//        Optional<ChatbotMapping> existingMappingOpt = chatbotMappingRepository.findByCustomerEmail(customerEmail);
//
//        if (existingMappingOpt.isPresent()) {
//            ChatbotMapping mapping = existingMappingOpt.get();
//
//            String currentPort = mapping.getPort(); // explicitly capture port
//
//            mapping.setApiKey(apiKey); // update only API key
//
//            // Explicitly re-set the port even if unchanged to guarantee it remains in the object
//            mapping.setPort(currentPort);
//
//            chatbotMappingRepository.save(mapping);
//            logger.info("Updated API Key while retaining existing Port: {} for CustomerEmail: {}", currentPort, customerEmail);
//        } else {
//            logger.info("No mapping found for CustomerEmail: {}. Skipping update, as creation is not required.", customerEmail);
//        }
//    }
//
//}
package com.zapcom.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.zapcom.entity.ChatbotMapping;
import com.zapcom.repository.ChatbotMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ChatbotMappingServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotMappingServiceImpl.class);

    @Autowired
    private ChatbotMappingRepository chatbotMappingRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Updates only the apiKey for an existing ChatbotMapping while retaining the manually set port.
     * Does NOT create a new document if one does not exist.
     *
     * @param customerEmail the customer's email (used as unique identifier)
     * @param apiKey        the new API key to set
     */
    public void saveMapping(String customerEmail, String apiKey) {
        logger.info("Updating only apiKey for customerEmail: {} while retaining port.", customerEmail);

        Query query = new Query(Criteria.where("customerEmail").is(customerEmail));
        Update update = new Update().set("apiKey", apiKey);

        UpdateResult result = mongoTemplate.updateFirst(query, update, ChatbotMapping.class);

        if (result.getMatchedCount() > 0) {
            logger.info("Successfully updated apiKey while retaining port for customerEmail: {}", customerEmail);
        } else {
            logger.info("No existing mapping found for customerEmail: {}. Skipping creation as required.", customerEmail);
        }
    }
}
