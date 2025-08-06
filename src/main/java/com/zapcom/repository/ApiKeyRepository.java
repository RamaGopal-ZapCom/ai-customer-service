package com.zapcom.repository;
import com.zapcom.entity.CustomerApiKeySchema;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ApiKeyRepository extends MongoRepository<CustomerApiKeySchema, String> {
    Optional<CustomerApiKeySchema> findByApiKey(String apiKey);
    Optional<CustomerApiKeySchema> findByCustomerEmail(String customerEmail);

}




