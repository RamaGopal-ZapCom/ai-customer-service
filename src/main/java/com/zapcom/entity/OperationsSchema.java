package com.zapcom.entity;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@Document
public class OperationsSchema {
    private OperatingHoursSchema operatingHours;
    private List<String> cuisineType;
    private String deliveryRadiusPreference;
    private StaffSchema staff;
    private List<String> websiteOrSocialMediaLinks;
}


