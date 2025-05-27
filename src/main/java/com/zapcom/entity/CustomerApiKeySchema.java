package com.zapcom.entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "api_keys")
public class CustomerApiKeySchema {
    @Id
    private String id;
    private String customerEmail;
    private String apiKey;
}

