package com.zapcom.repository;
import com.zapcom.entity.CustomerSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface CustomerServiceRepository extends MongoRepository<CustomerSchema, String> {
}

