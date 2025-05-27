package com.zapcom.entity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@Document
public class AgreementsSchema {
    private boolean termsAccepted;
    private boolean privacyPolicyAccepted;
}
