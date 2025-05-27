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
import com.zapcom.entity.CustomerApiKeySchema;
import com.zapcom.entity.CustomerProfileSchema;
import com.zapcom.entity.CustomerSchema;
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
import com.zapcom.model.LegalAndTaxCompliance;
import com.zapcom.model.MetaData;
import com.zapcom.model.OperatingHours;
import com.zapcom.model.Operations;
import com.zapcom.model.Staff;
import com.zapcom.model.Theme;
import com.zapcom.model.response.CustomerResponse;
import com.zapcom.repository.ApiKeyRepository;
import com.zapcom.repository.CustomerServiceRepository;
import com.zapcom.service.CustomerService;
import org.springframework.stereotype.Service;
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerServiceRepository customerRepository;
    private final ApiKeyRepository apiKeyRepository;
    public CustomerServiceImpl(
            CustomerServiceRepository customerRepository,
            ApiKeyRepository apiKeyRepository) {
        this.customerRepository = customerRepository;
        this.apiKeyRepository = apiKeyRepository;
    }
    @Override
    public CustomerResponse saveCustomerRequest(CustomerRequest customerRequest) {
        if (customerRequest == null) {
            throw new CustomerServiceException(CustomerServiceMessageConstants.ERROR_NULL_CUSTOMER_REQUEST);
        }
        CustomerProfile customerProfile = customerRequest.getCustomerProfile();
        if (customerProfile == null) {
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "customerProfile"));
        }
        Address registeredAddress = customerProfile.getRegisteredAddress();
        if (registeredAddress == null) {
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
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "adminDetails"));
        }

        AdminPrimary primary = adminDetails.getPrimary();
        if (primary == null) {
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "adminDetails.primary"));
        }

        AdminTechnical technical = adminDetails.getTechnical();
        if (technical == null) {
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
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "metaData"));
        }
        MetaDataSchema metaDataSchema = MetaDataSchema.builder()
                .note(metaData.getNote())
                .build();

        Operations operations = customerRequest.getOperations();
        if (operations == null) {
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "operations"));
        }

        OperatingHours operatingHours = operations.getOperatingHours();
        if (operatingHours == null) {
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "operations.operatingHours"));
        }

        Staff staff = operations.getStaff();
        if (staff == null) {
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
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, "chatbotConfig"));
        }
        Theme theme = chatbotConfig.getTheme();
        if (theme == null) {
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
        return CustomerResponse.builder()
                .message(CustomerServiceMessageConstants.SUCCESS_ACCOUNT_CREATED)
                .build();
    }
    @Override
    public CustomerResponse saveApiKey(CustomerApiKeySchema customerApiKeySchema) {
        if (customerApiKeySchema == null) {
            throw new CustomerServiceException(CustomerServiceMessageConstants.ERROR_NULL_CUSTOMER_REQUEST);
        }
        if (customerApiKeySchema.getApiKey() == null || customerApiKeySchema.getApiKey().trim().isEmpty()) {
            throw new CustomerServiceException(CustomerServiceMessageConstants.ERROR_API_KEY_NULL_OR_EMPTY);
        }
        if (customerApiKeySchema.getCustomerEmail() == null || customerApiKeySchema.getCustomerEmail().trim().isEmpty()) {
            throw new CustomerServiceException(CustomerServiceMessageConstants.ERROR_CUSTOMER_EMAIL_NULL_OR_EMPTY);
        }

        CustomerApiKeySchema existingByKey = apiKeyRepository.findByApiKey(customerApiKeySchema.getApiKey());
        if (existingByKey != null && !existingByKey.getCustomerEmail().equals(customerApiKeySchema.getCustomerEmail())) {
            throw new CustomerServiceException(CustomerServiceMessageConstants.ERROR_API_KEY_ALREADY_IN_USE);
        }

        CustomerApiKeySchema existingByEmail = apiKeyRepository.findByCustomerEmail(customerApiKeySchema.getCustomerEmail());
        if (existingByEmail != null) {
            existingByEmail.setApiKey(customerApiKeySchema.getApiKey());
            apiKeyRepository.save(existingByEmail);
        } else {
            apiKeyRepository.save(customerApiKeySchema);
        }

        return CustomerResponse.builder()
                .message(CustomerServiceMessageConstants.SUCCESS_API_KEY_STORED)
                .build();
    }
    private void validateStringField(String value, String fieldName) {
        if (value == null) {
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_NULL_FIELD, fieldName));
        }
        if (value.trim().isEmpty()) {
            throw new CustomerServiceException(String.format(CustomerServiceMessageConstants.ERROR_EMPTY_FIELD, fieldName));
        }
    }
}

