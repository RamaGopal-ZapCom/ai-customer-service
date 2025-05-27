package com.zapcom.entity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@Document
public class BrandingSchema {
    private String logoUrl;
    private String brandName;
    private String greetingMessage;
    private String fallBackResponse;
}
