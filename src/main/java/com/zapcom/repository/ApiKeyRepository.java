package com.zapcom.repository;
import com.zapcom.entity.CustomerApiKeySchema;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface ApiKeyRepository extends MongoRepository<CustomerApiKeySchema, String> {
    CustomerApiKeySchema findByApiKey(String apiKey);
    CustomerApiKeySchema findByCustomerEmail(String customerEmail);
}

