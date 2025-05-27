package com.zapcom.entity;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class CustomerRequestSchema {
    private CustomerProfileSchema customerProfile;
    private LegalAndTaxComplianceSchema legalAndTaxCompliance;
    private BankingDetailsSchema bankingDetails;
    private AdminDetailsSchema adminDetails;
    private MetaDataSchema metaData;
    private OperationsSchema operations;
    private ApiConfigurationSchema apiConfiguration;
    private BrandingSchema branding;
    private ChatbotConfigSchema chatbotConfig;
    private AgreementsSchema agreements;
}

