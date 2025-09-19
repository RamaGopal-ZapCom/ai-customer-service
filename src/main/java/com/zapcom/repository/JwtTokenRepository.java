package com.zapcom.repository;

import com.zapcom.entity.JwtTokenSchema;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JwtTokenRepository extends MongoRepository<JwtTokenSchema, String> {

    // ✅ Now returns Optional so it can be safely used in service
    Optional<JwtTokenSchema> findByCustomerEmail(String customerEmail);

    Optional<JwtTokenSchema> findByJwtToken(String jwtToken);
}
