package com.zapcom.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")
public class CustomerSchema {
    @Id
    private String id;
    private CustomerProfileSchema customerProfileSchema;
    private LegalAndTaxComplianceSchema legalAndTaxComplianceSchema;
    private BankingDetailsSchema bankingDetailsSchema;
    private AdminDetailsSchema adminDetailsSchema;
    private MetaDataSchema metaDataSchema;
    private OperationsSchema operationsSchema;
    private ApiConfigurationSchema apiConfigurationSchema;
    private BrandingSchema brandingSchema;
    private ChatbotConfigSchema chatbotConfigSchema;
    private AgreementsSchema agreementsSchema;
}
