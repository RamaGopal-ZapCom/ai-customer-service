package com.zapcom.repository;

import com.zapcom.entity.CustomerSchema;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CustomerServiceRepository extends MongoRepository<CustomerSchema, String> {
    Optional<CustomerSchema> findById(String id);

    CustomerSchema findByCustomerProfileSchemaCustomerEmail(String email);
}

