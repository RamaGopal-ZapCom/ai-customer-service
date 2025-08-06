
package com.zapcom.service.impl;

import com.zapcom.constants.CustomerServiceMessageConstants;
import com.zapcom.entity.AddressSchema;
import com.zapcom.entity.AdminDetailsSchema;
import com.zapcom.entity.AdminPrimarySchema;
import com.zapcom.entity.AdminTechnicalSchema;
import com.zapcom.entity.AgreementsSchema;
import com.zapcom.entity.ApiConfigurationSchema;
import com.zapcom.entity.BankingDetailsSchema;
import com.zapcom.entity.BrandingSchema;
import com.zapcom.entity.ChatbotConfigSchema;
import com.zapcom.entity.ChatbotMapping;
import com.zapcom.entity.CustomerApiKeySchema;
import com.zapcom.entity.CustomerProfileSchema;
import com.zapcom.entity.CustomerSchema;
import com.zapcom.entity.JwtTokenSchema;
import com.zapcom.entity.LegalAndTaxComplianceSchema;
import com.zapcom.entity.MetaDataSchema;
import com.zapcom.entity.OperatingHoursSchema;
import com.zapcom.entity.OperationsSchema;
import com.zapcom.entity.StaffSchema;
import com.zapcom.entity.ThemeSchema;
import com.zapcom.exceptions.CustomerServiceException;
import com.zapcom.model.Address;
import com.zapcom.model.AdminDetails;
import com.zapcom.model.AdminPrimary;
import com.zapcom.model.AdminTechnical;
import com.zapcom.model.Agreements;
import com.zapcom.model.ApiConfiguration;
import com.zapcom.model.BankingDetails;
import com.zapcom.model.Branding;
import com.zapcom.model.ChatbotConfig;
import com.zapcom.model.CustomerProfile;
import com.zapcom.model.CustomerRequest;
import com.zapcom.model.JwtTokenRequest;
import com.zapcom.model.LegalAndTaxCompliance;
import com.zapcom.model.MetaData;
import com.zapcom.model.OperatingHours;
import com.zapcom.model.Operations;
import com.zapcom.model.Staff;
import com.zapcom.model.Theme;
import com.zapcom.model.response.CustomerResponse;
import com.zapcom.model.response.CustomerUIResponse;
import com.zapcom.repository.ApiKeyRepository;
import com.zapcom.repository.ChatbotMappingRepository;
import com.zapcom.repository.CustomerServiceRepository;
import com.zapcom.repository.JwtTokenRepository;
import com.zapcom.service.CustomerService;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
@Autowired
    private final CustomerServiceRepository customerRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final ChatbotMappingRepository chatbotMappingRepository;
    private final JwtTokenRepository jwtTokenRepository;

    public CustomerServiceImpl(
            CustomerServiceRepository customerRepository,
            ApiKeyRepository apiKeyRepository,
            ChatbotMappingRepository chatbotMappingRepository,
            JwtTokenRepository jwtTokenRepository
    ) {
        this.customerRepository = customerRepository;
        this.apiKeyRepository = apiKeyRepository;
        this.chatbotMappingRepository = chatbotMappingRepository;
        this.jwtTokenRepository = jwtTokenRepository;
    }


    @Override
    public void storeJwtToken(JwtTokenRequest request) {
        if (request == null || request.getCustomerEmail() == null || request.getCustomerEmail().trim().isEmpty()) {
            throw new CustomerServiceException("Customer Email is required.");
        }
        if (request.getJwtToken() == null || request.getJwtToken().trim().isEmpty()) {
            throw new CustomerServiceException("JWT Token is required.");
        }

        Optional<JwtTokenSchema> existingTokenOpt = jwtTokenRepository.findByCustomerEmail(request.getCustomerEmail());

        JwtTokenSchema tokenSchema;

        if (existingTokenOpt.isPresent()) {
            tokenSchema = existingTokenOpt.get();
            tokenSchema.setJwtToken(request.getJwtToken()); // Update with new JWT
        } else {
            tokenSchema = JwtTokenSchema.builder()
                    .customerEmail(request.getCustomerEmail())
                    .jwtToken(request.getJwtToken())
                    .build();
        }

        jwtTokenRepository.save(tokenSchema);
    }


    @Override
    public CustomerResponse saveCustomerRequest(CustomerRequest customerRequest) {
        logger.info("Saving customer request for customer: {}",
                customerRequest != null && customerRequest.getCustomerProfile() != null
                        ? customerRequest.getCustomerProfile().getCustomerName()
                        : "Unknown");

        if (customerRequest == null) {
            logger.error("CustomerRequest object is null");
            throw new CustomerServiceException(CustomerServiceMessageConstants.ERROR_NULL_CUSTOMER_REQUEST);
        }

        CustomerProfile customerProfile = customerRequest.getCustomerProfile();
        if (customerProfile == null) {
            logger.error("CustomerProfile is null in CustomerRequest");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "customerProfile"));
        }

        Address registeredAddress = customerProfile.getRegisteredAddress();
        if (registeredAddress == null) {
            logger.error("RegisteredAddress is null in CustomerProfile");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "registeredAddress"));
        }

        validateStringField(customerProfile.getCustomerName(), "customerName");
        validateStringField(customerProfile.getIndustryType(), "industryType");
        validateStringField(customerProfile.getCountry(), "country");

        AddressSchema registeredAddressSchema = AddressSchema.builder()
                .street(registeredAddress.getStreet())
                .city(registeredAddress.getCity())
                .state(registeredAddress.getState())
                .pinCode(registeredAddress.getPinCode())
                .build();

        CustomerProfileSchema customerProfileSchema = CustomerProfileSchema.builder()
                .customerName(customerProfile.getCustomerName())
                .industryType(customerProfile.getIndustryType())
                .customerWebsite(customerProfile.getCustomerWebsite())
                .businessCategory(customerProfile.getBusinessCategory())
                .country(customerProfile.getCountry())
                .registeredAddress(registeredAddressSchema)
                .customerRegistrationNumber(customerProfile.getCustomerRegistrationNumber())
                .ownerName(customerProfile.getOwnerName())
                .contactNumber(customerProfile.getContactNumber())
                .customerEmail(customerProfile.getCustomerEmail())
                .build();

        LegalAndTaxCompliance legal = customerRequest.getLegalAndTaxCompliance();
        if (legal == null) {
            logger.error("LegalAndTaxCompliance is null in CustomerRequest");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "legalAndTaxCompliance"));
        }

        LegalAndTaxComplianceSchema legalAndTaxComplianceSchema = LegalAndTaxComplianceSchema.builder()
                .gstNumber(legal.getGstNumber())
                .panCard(legal.getPanCard())
                .fssaiLicense(legal.getFssaiLicense())
                .shopsAndEstablishmentLicense(legal.getShopsAndEstablishmentLicense())
                .tradeLicense(legal.getTradeLicense())
                .build();

        BankingDetails banking = customerRequest.getBankingDetails();
        if (banking == null) {
            logger.error("BankingDetails is null in CustomerRequest");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "bankingDetails"));
        }

        BankingDetailsSchema bankingDetailsSchema = BankingDetailsSchema.builder()
                .bankAccountNumber(banking.getBankAccountNumber())
                .bankName(banking.getBankName())
                .ifscCode(banking.getIfscCode())
                .cancelledChequeOrBankStatement(banking.getCancelledChequeOrBankStatement())
                .build();

        AdminDetails adminDetails = customerRequest.getAdminDetails();
        if (adminDetails == null) {
            logger.error("AdminDetails is null in CustomerRequest");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "adminDetails"));
        }

        AdminPrimary primary = adminDetails.getPrimary();
        if (primary == null) {
            logger.error("AdminPrimary is null in AdminDetails");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "adminDetails.primary"));
        }

        AdminTechnical technical = adminDetails.getTechnical();
        if (technical == null) {
            logger.error("AdminTechnical is null in AdminDetails");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "adminDetails.technical"));
        }

        AdminPrimarySchema adminPrimarySchema = AdminPrimarySchema.builder()
                .name(primary.getName())
                .phone(primary.getPhone())
                .address(primary.getAddress())
                .timeZone(primary.getTimeZone())
                .build();

        AdminTechnicalSchema adminTechnicalSchema = AdminTechnicalSchema.builder()
                .name(technical.getName())
                .email(technical.getEmail())
                .phone(technical.getPhone())
                .build();

        AdminDetailsSchema adminDetailsSchema = AdminDetailsSchema.builder()
                .primary(adminPrimarySchema)
                .technical(adminTechnicalSchema)
                .build();

        MetaData metaData = customerRequest.getMetaData();
        if (metaData == null) {
            logger.error("MetaData is null in CustomerRequest");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "metaData"));
        }

        MetaDataSchema metaDataSchema = MetaDataSchema.builder()
                .note(metaData.getNote())
                .build();

        Operations operations = customerRequest.getOperations();
        if (operations == null) {
            logger.error("Operations is null in CustomerRequest");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "operations"));
        }

        OperatingHours operatingHours = operations.getOperatingHours();
        if (operatingHours == null) {
            logger.error("OperatingHours is null in Operations");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "operations.operatingHours"));
        }

        Staff staff = operations.getStaff();
        if (staff == null) {
            logger.error("Staff is null in Operations");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "operations.staff"));
        }

        OperatingHoursSchema operatingHoursSchema = OperatingHoursSchema.builder()
                .openingTime(operatingHours.getOpeningTime())
                .closingTime(operatingHours.getClosingTime())
                .build();

        StaffSchema staffSchema = StaffSchema.builder()
                .kitchenStaff(staff.getKitchenStaff())
                .deliveryStaff(staff.getDeliveryStaff())
                .build();

        OperationsSchema operationsSchema = OperationsSchema.builder()
                .operatingHours(operatingHoursSchema)
                .cuisineType(operations.getCuisineType())
                .deliveryRadiusPreference(operations.getDeliveryRadiusPreference())
                .staff(staffSchema)
                .websiteOrSocialMediaLinks(operations.getWebsiteOrSocialMediaLinks())
                .build();

        ApiConfiguration apiConfiguration = customerRequest.getApiConfiguration();
        if (apiConfiguration == null) {
            logger.error("ApiConfiguration is null in CustomerRequest");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "apiConfiguration"));
        }

        ApiConfigurationSchema apiConfigurationSchema = ApiConfigurationSchema.builder()
                .estimatedMonthlyRequests(apiConfiguration.getEstimatedMonthlyRequests())
                .requestsPerMinute(apiConfiguration.getRequestsPerMinute())
                .peakUsageHours(apiConfiguration.getPeakUsageHours())
                .botPurpose(apiConfiguration.getBotPurpose())
                .complianceStandards(apiConfiguration.getComplianceStandards())
                .build();

        Branding branding = customerRequest.getBranding();
        if (branding == null) {
            logger.error("Branding is null in CustomerRequest");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "branding"));
        }

        BrandingSchema brandingSchema = BrandingSchema.builder()
                .logoUrl(branding.getLogoUrl())
                .brandName(branding.getBrandName())
                .greetingMessage(branding.getGreetingMessage())
                .fallBackResponse(branding.getFallBackResponse())
                .build();

        ChatbotConfig chatbotConfig = customerRequest.getChatbotConfig();
        if (chatbotConfig == null) {
            logger.error("ChatbotConfig is null in CustomerRequest");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "chatbotConfig"));
        }

        Theme theme = chatbotConfig.getTheme();
        if (theme == null) {
            logger.error("Theme is null in ChatbotConfig");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "chatbotConfig.theme"));
        }

        ThemeSchema themeSchema = ThemeSchema.builder()
                .primaryColor(theme.getPrimaryColor())
                .secondaryColor(theme.getSecondaryColor())
                .fontStyle(theme.getFontStyle())
                .build();

        ChatbotConfigSchema chatbotConfigSchema = ChatbotConfigSchema.builder()
                .theme(themeSchema)
                .supportedLanguages(chatbotConfig.getSupportedLanguages())
                .chatWidgetPosition(chatbotConfig.getChatWidgetPosition())
                .build();

        Agreements agreements = customerRequest.getAgreements();
        if (agreements == null) {
            logger.error("Agreements is null in CustomerRequest");
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "agreements"));
        }

        AgreementsSchema agreementsSchema = AgreementsSchema.builder()
                .termsAccepted(agreements.isTermsAccepted())
                .privacyPolicyAccepted(agreements.isPrivacyPolicyAccepted())
                .build();

        CustomerSchema customerSchema = CustomerSchema.builder()
                .customerProfileSchema(customerProfileSchema)
                .legalAndTaxComplianceSchema(legalAndTaxComplianceSchema)
                .bankingDetailsSchema(bankingDetailsSchema)
                .adminDetailsSchema(adminDetailsSchema)
                .metaDataSchema(metaDataSchema)
                .operationsSchema(operationsSchema)
                .apiConfigurationSchema(apiConfigurationSchema)
                .brandingSchema(brandingSchema)
                .chatbotConfigSchema(chatbotConfigSchema)
                .agreementsSchema(agreementsSchema)
                .build();

        customerRepository.save(customerSchema);
        logger.info("Customer request saved successfully for customer: {}", customerProfile.getCustomerName());

        return CustomerResponse.builder()
                .message(CustomerServiceMessageConstants.SUCCESS_ACCOUNT_CREATED)
                .build();
    }

    @Override
    public CustomerResponse saveApiKey(CustomerApiKeySchema customerApiKeySchema) {
        logger.info("Saving API Key for customer email: {}",
                customerApiKeySchema != null ? customerApiKeySchema.getCustomerEmail() : "Unknown");

        if (customerApiKeySchema == null) {
            logger.error("CustomerApiKeySchema object is null");
            throw new CustomerServiceException(CustomerServiceMessageConstants.ERROR_NULL_CUSTOMER_REQUEST);
        }

        if (customerApiKeySchema.getApiKey() == null || customerApiKeySchema.getApiKey().trim().isEmpty()) {
            logger.error("API Key is null or empty");
            throw new CustomerServiceException(CustomerServiceMessageConstants.ERROR_API_KEY_NULL_OR_EMPTY);
        }

        if (customerApiKeySchema.getCustomerEmail() == null || customerApiKeySchema.getCustomerEmail().trim().isEmpty()) {
            logger.error("Customer Email is null or empty");
            throw new CustomerServiceException(CustomerServiceMessageConstants.ERROR_CUSTOMER_EMAIL_NULL_OR_EMPTY);
        }

        CustomerApiKeySchema existingByKey = apiKeyRepository.findByApiKey(customerApiKeySchema.getApiKey())
                .orElse(null);

        if (existingByKey != null && !existingByKey.getCustomerEmail().equals(customerApiKeySchema.getCustomerEmail())) {
            logger.error("API Key already in use by another customer");
            throw new CustomerServiceException(CustomerServiceMessageConstants.ERROR_API_KEY_ALREADY_IN_USE);
        }

        CustomerApiKeySchema existingByEmail = apiKeyRepository.findByCustomerEmail(customerApiKeySchema.getCustomerEmail())
                .orElse(null);

        if (existingByEmail != null) {
            existingByEmail.setApiKey(customerApiKeySchema.getApiKey());
            apiKeyRepository.save(existingByEmail);
            logger.info("Updated existing API Key for customer: {}", customerApiKeySchema.getCustomerEmail());
        } else {
            apiKeyRepository.save(customerApiKeySchema);
            logger.info("Saved new API Key for customer: {}", customerApiKeySchema.getCustomerEmail());
        }

        Optional<ChatbotMapping> existingMapping = chatbotMappingRepository.findByCustomerEmail(customerApiKeySchema.getCustomerEmail());

        ChatbotMapping mapping;

        if (existingMapping.isPresent()) {
            mapping = existingMapping.get();
            mapping.setApiKey(customerApiKeySchema.getApiKey());
            logger.info("Updated API Key for existing ChatbotMapping for customer email: {}", customerApiKeySchema.getCustomerEmail());
        } else {
            mapping = ChatbotMapping.builder()
                    .customerEmail(customerApiKeySchema.getCustomerEmail())
                    .apiKey(customerApiKeySchema.getApiKey())
                    .build();
            logger.info("Created new ChatbotMapping for customer email: {} with API Key: {}", customerApiKeySchema.getCustomerEmail(), customerApiKeySchema.getApiKey());
        }

        chatbotMappingRepository.save(mapping);


        return CustomerResponse.builder()
                .message(CustomerServiceMessageConstants.SUCCESS_API_KEY_STORED)
                .build();
    }

    private void validateStringField(String value, String fieldName) {
        if (value == null) {
            logger.error("Field '{}' is null", fieldName);
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, fieldName));
        }
        if (value.trim().isEmpty()) {
            logger.error("Field '{}' is empty", fieldName);
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_EMPTY_FIELD, fieldName));
        }
    }

    @Override
    public CustomerUIResponse getCustomerDetails(String jwtToken) {
        JwtTokenSchema jwtTokenSchema = jwtTokenRepository.findByJwtToken(jwtToken)
                .orElseThrow(() -> new CustomerServiceException("Invalid JWT token."));

        String email = jwtTokenSchema.getCustomerEmail();
        CustomerSchema customerSchema = customerRepository.findByCustomerProfileSchemaCustomerEmail(email);

        if (customerSchema == null) {
            throw new CustomerServiceException("No customer found for the provided token.");
        }

        return CustomerUIResponse.builder()
                .customerProfile(convert(customerSchema.getCustomerProfileSchema()))
                .legalAndTaxCompliance(convert(customerSchema.getLegalAndTaxComplianceSchema()))
                .bankingDetails(convert(customerSchema.getBankingDetailsSchema()))
                .adminDetails(convert(customerSchema.getAdminDetailsSchema()))
                .metaData(convert(customerSchema.getMetaDataSchema()))
                .operations(convert(customerSchema.getOperationsSchema()))
                .apiConfiguration(convert(customerSchema.getApiConfigurationSchema()))
                .branding(convert(customerSchema.getBrandingSchema()))
                .chatbotConfig(convert(customerSchema.getChatbotConfigSchema()))
                .agreements(convert(customerSchema.getAgreementsSchema()))
                .build();
    }
    private Address convert(AddressSchema schema) {
        if (schema == null) return null;

        return Address.builder()
                .street(schema.getStreet())
                .city(schema.getCity())
                .state(schema.getState())
                .pinCode(schema.getPinCode())
                .build();
    }

    private CustomerProfile convert(CustomerProfileSchema schema) {
        if (schema == null) return null;

        return CustomerProfile.builder()
                .customerName(schema.getCustomerName())
                .industryType(schema.getIndustryType())
                .businessCategory(schema.getBusinessCategory())
                .registeredAddress(convert(schema.getRegisteredAddress()))
                .country(schema.getCountry())
                .gstNumber(schema.getGstNumber())
                .customerWebsite(schema.getCustomerWebsite())
                .customerRegistrationNumber(schema.getCustomerRegistrationNumber())
                .ownerName(schema.getOwnerName())
                .contactNumber(schema.getContactNumber())
                .customerEmail(schema.getCustomerEmail())
                .build();
    }



    private LegalAndTaxCompliance convert(LegalAndTaxComplianceSchema schema) {
        if (schema == null) return null;
        return LegalAndTaxCompliance.builder()
                .gstNumber(schema.getGstNumber())
                .panCard(schema.getPanCard())
                .fssaiLicense(schema.getFssaiLicense())
                .shopsAndEstablishmentLicense(schema.getShopsAndEstablishmentLicense())
                .tradeLicense(schema.getTradeLicense())
                .build();
    }

    private BankingDetails convert(BankingDetailsSchema schema) {
        if (schema == null) return null;
        return BankingDetails.builder()
                .bankAccountNumber(schema.getBankAccountNumber())
                .bankName(schema.getBankName())
                .ifscCode(schema.getIfscCode())
                .cancelledChequeOrBankStatement(schema.getCancelledChequeOrBankStatement())
                .build();
    }

    private AdminDetails convert(AdminDetailsSchema schema) {
        if (schema == null) return null;

        AdminPrimary primary = schema.getPrimary() != null
                ? AdminPrimary.builder()
                .name(schema.getPrimary().getName())
                .phone(schema.getPrimary().getPhone())
                .address(schema.getPrimary().getAddress())
                .timeZone(schema.getPrimary().getTimeZone())
                .build()
                : null;

        AdminTechnical technical = schema.getTechnical() != null
                ? AdminTechnical.builder()
                .name(schema.getTechnical().getName())
                .email(schema.getTechnical().getEmail())
                .phone(schema.getTechnical().getPhone())
                .build()
                : null;

        return AdminDetails.builder()
                .primary(primary)
                .technical(technical)
                .build();
    }

    private MetaData convert(MetaDataSchema schema) {
        if (schema == null) return null;
        return MetaData.builder()
                .note(schema.getNote())
                .build();
    }

    private Operations convert(OperationsSchema schema) {
        if (schema == null) return null;

        OperatingHours hours = schema.getOperatingHours() != null
                ? OperatingHours.builder()
                .openingTime(schema.getOperatingHours().getOpeningTime())
                .closingTime(schema.getOperatingHours().getClosingTime())
                .build()
                : null;

        Staff staff = schema.getStaff() != null
                ? Staff.builder()
                .kitchenStaff(schema.getStaff().getKitchenStaff())
                .deliveryStaff(schema.getStaff().getDeliveryStaff())
                .build()
                : null;

        return Operations.builder()
                .operatingHours(hours)
                .cuisineType(schema.getCuisineType())
                .deliveryRadiusPreference(schema.getDeliveryRadiusPreference())
                .staff(staff)
                .websiteOrSocialMediaLinks(schema.getWebsiteOrSocialMediaLinks())
                .build();
    }

    private ApiConfiguration convert(ApiConfigurationSchema schema) {
        if (schema == null) return null;
        return ApiConfiguration.builder()
                .estimatedMonthlyRequests(schema.getEstimatedMonthlyRequests())
                .requestsPerMinute(schema.getRequestsPerMinute())
                .peakUsageHours(schema.getPeakUsageHours())
                .botPurpose(schema.getBotPurpose())
                .complianceStandards(schema.getComplianceStandards())
                .build();
    }

    private Branding convert(BrandingSchema schema) {
        if (schema == null) return null;
        return Branding.builder()
                .logoUrl(schema.getLogoUrl())
                .brandName(schema.getBrandName())
                .greetingMessage(schema.getGreetingMessage())
                .fallBackResponse(schema.getFallBackResponse())
                .build();
    }

    private ChatbotConfig convert(ChatbotConfigSchema schema) {
        if (schema == null) return null;

        Theme theme = schema.getTheme() != null
                ? Theme.builder()
                .primaryColor(schema.getTheme().getPrimaryColor())
                .secondaryColor(schema.getTheme().getSecondaryColor())
                .fontStyle(schema.getTheme().getFontStyle())
                .build()
                : null;

        return ChatbotConfig.builder()
                .theme(theme)
                .supportedLanguages(schema.getSupportedLanguages())
                .chatWidgetPosition(schema.getChatWidgetPosition())
                .build();
    }

    private Agreements convert(AgreementsSchema schema) {
        if (schema == null) return null;
        return Agreements.builder()
                .termsAccepted(schema.isTermsAccepted())
                .privacyPolicyAccepted(schema.isPrivacyPolicyAccepted())
                .build();
    }
}
