package com.zapcom.entity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@Document
public class CustomerProfileSchema {
    private String customerName;
    private String industryType;
    private String businessCategory;
    private AddressSchema registeredAddress;
    private String country;
    private String gstNumber;
    private String customerWebsite;
    private String customerRegistrationNumber;
    private String ownerName;
    private String contactNumber;
    private String customerEmail;
}
