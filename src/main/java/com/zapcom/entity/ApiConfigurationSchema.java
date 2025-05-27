package com.zapcom.entity;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@Document
public class ApiConfigurationSchema {
        private Integer estimatedMonthlyRequests;
        private Integer requestsPerMinute;
        private String peakUsageHours;
        private String botPurpose;
        private List< String> complianceStandards;
    }


