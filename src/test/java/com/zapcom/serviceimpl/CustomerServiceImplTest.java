package com.zapcom.serviceimpl;

import com.zapcom.constants.ControllerTestConstants;
import com.zapcom.constants.ServiceImplTestConstants;
import com.zapcom.entity.CustomerApiKeySchema;
import com.zapcom.entity.CustomerSchema;
import com.zapcom.exceptions.CustomerServiceException;
import com.zapcom.model.*;
import com.zapcom.model.response.CustomerResponse;
import com.zapcom.repository.ApiKeyRepository;
import com.zapcom.repository.CustomerServiceRepository;
import com.zapcom.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static com.zapcom.constants.ServiceImplTestConstants.CUSTOMER_NAME;
import static com.zapcom.constants.ServiceImplTestConstants.INDUSTRY_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


class CustomerServiceImplTest {
    @Mock
    private CustomerServiceRepository customerRepository;
    @Mock
    private ApiKeyRepository apiKeyRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testSaveCustomerRequest_Success() {
        CustomerProfile profile = CustomerProfile.builder()
                .customerName(ControllerTestConstants.CUSTOMER_NAME)
                .industryType(ControllerTestConstants.INDUSTRY_TYPE)
                .businessCategory(ControllerTestConstants.BUSINESS_CATEGORY)
                .registeredAddress(Address.builder()
                        .street(null)
                        .city(ControllerTestConstants.CITY)
                        .state(ControllerTestConstants.STATE)
                        .pinCode(null)
                        .build())
                .country(ControllerTestConstants.COUNTRY)
                .gstNumber(ControllerTestConstants.GST_NUMBER)
                .customerWebsite(ControllerTestConstants.WEBSITE)
                .customerRegistrationNumber(ControllerTestConstants.REGISTRATION_NO)
                .ownerName(null)
                .contactNumber(null)
                .customerEmail(ControllerTestConstants.EMAIL)
                .build();

        LegalAndTaxCompliance legal = LegalAndTaxCompliance.builder()
                .gstNumber(null)
                .panCard(null)
                .fssaiLicense(null)
                .shopsAndEstablishmentLicense(null)
                .tradeLicense(null)
                .build();

        BankingDetails banking = BankingDetails.builder()
                .bankAccountNumber(null)
                .bankName(null)
                .ifscCode(null)
                .cancelledChequeOrBankStatement(null)
                .build();

        AdminDetails admin = AdminDetails.builder()
                .primary(AdminPrimary.builder()
                        .name(ControllerTestConstants.PRIMARY_NAME)
                        .phone(ControllerTestConstants.PRIMARY_PHONE)
                        .address(ControllerTestConstants.PRIMARY_ADDRESS)
                        .timeZone(ControllerTestConstants.TIME_ZONE)
                        .build())
                .technical(AdminTechnical.builder()
                        .name(ControllerTestConstants.TECH_NAME)
                        .email(ControllerTestConstants.TECH_EMAIL)
                        .phone(ControllerTestConstants.TECH_PHONE)
                        .build())
                .build();

        MetaData metaData = MetaData.builder().build();

        Operations operations = Operations.builder()
                .operatingHours(OperatingHours.builder()
                        .openingTime(null)
                        .closingTime(null)
                        .build())
                .cuisineType(Collections.singletonList(""))
                .deliveryRadiusPreference(null)
                .staff(Staff.builder()
                        .kitchenStaff(0)
                        .deliveryStaff(0)
                        .build())
                .websiteOrSocialMediaLinks(Collections.singletonList(""))
                .build();

        ApiConfiguration api = ApiConfiguration.builder()
                .estimatedMonthlyRequests(ControllerTestConstants.ESTIMATED_REQUESTS)
                .requestsPerMinute(ControllerTestConstants.REQUESTS_PER_MIN)
                .peakUsageHours(ControllerTestConstants.PEAK_HOURS)
                .botPurpose(ControllerTestConstants.BOT_PURPOSE)
                .complianceStandards(Arrays.asList(ControllerTestConstants.COMPLIANCE_1, ControllerTestConstants.COMPLIANCE_2, ControllerTestConstants.COMPLIANCE_3))
                .build();

        Branding branding = Branding.builder()
                .logoUrl(ControllerTestConstants.LOGO_URL)
                .brandName(ControllerTestConstants.BRAND_NAME)
                .greetingMessage(ControllerTestConstants.GREETING_MSG)
                .fallBackResponse(ControllerTestConstants.FALLBACK_MSG)
                .build();

        ChatbotConfig chatbot = ChatbotConfig.builder()
                .theme(Theme.builder()
                        .primaryColor(ControllerTestConstants.PRIMARY_COLOR)
                        .secondaryColor(ControllerTestConstants.SECONDARY_COLOR)
                        .fontStyle(ControllerTestConstants.FONT_STYLE)
                        .build())
                .supportedLanguages(ControllerTestConstants.lang1)
                .chatWidgetPosition(ControllerTestConstants.CHAT_POSITION)
                .build();

        Agreements agreements = Agreements.builder()
                .termsAccepted(true)
                .privacyPolicyAccepted(true)
                .build();

        CustomerRequest request = CustomerRequest.builder()
                .customerProfile(profile)
                .legalAndTaxCompliance(legal)
                .bankingDetails(banking)
                .adminDetails(admin)
                .metaData(metaData)
                .operations(operations)
                .apiConfiguration(api)
                .branding(branding)
                .chatbotConfig(chatbot)
                .agreements(agreements)
                .build();

        when(customerRepository.save(any(CustomerSchema.class))).thenReturn(new CustomerSchema());

        CustomerResponse result = customerService.saveCustomerRequest(request);
        assertEquals("Your account is successfully created", result.getMessage());
    }

    @Test
    void testSaveCustomerRequest_NullCustomerRequest_ThrowsException() {
        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () ->
                customerService.saveCustomerRequest(null)
        );
        assertEquals("Customer request cannot be null", exception.getMessage());
    }
    @Test
    void testSaveCustomerRequest_NullCustomerProfile_ThrowsException() {
        CustomerRequest request = CustomerRequest.builder().customerProfile(null).build();
        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () ->
                customerService.saveCustomerRequest(request)
        );
        assertEquals("Required field 'customerProfile' cannot be null", exception.getMessage());
    }
    @Test
    void testSaveCustomerRequest_NullRegisteredAddress_ThrowsException() {
        CustomerRequest request = CustomerRequest.builder()
                .customerProfile(CustomerProfile.builder().registeredAddress(null).build())
                .build();
        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () ->
                customerService.saveCustomerRequest(request)
        );
        assertEquals("Required field 'registeredAddress' cannot be null", exception.getMessage());
    }
    @Test
    void testSaveCustomerRequest_NullCustomerName_ThrowsException() {
        CustomerRequest request = CustomerRequest.builder()
                .customerProfile(CustomerProfile.builder()
                        .industryType(INDUSTRY_TYPE)
                        .country("India")
                        .registeredAddress(Address.builder().build())
                        .build())
                .legalAndTaxCompliance(LegalAndTaxCompliance.builder().build())
                .bankingDetails(BankingDetails.builder().build())
                .adminDetails(AdminDetails.builder()
                        .primary(AdminPrimary.builder().build())
                        .technical(AdminTechnical.builder().build())
                        .build())
                .build();

        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () ->
                customerService.saveCustomerRequest(request)
        );
        assertEquals("Required field 'customerName' cannot be null", exception.getMessage());
    }
    @Test
    void testSaveCustomerRequest_EmptyIndustryType_ThrowsException() {
        CustomerRequest request = CustomerRequest.builder()
                .customerProfile(CustomerProfile.builder()
                        .customerName(CUSTOMER_NAME)
                        .industryType("")
                        .country("India")
                        .registeredAddress(Address.builder().build())
                        .build())
                .legalAndTaxCompliance(LegalAndTaxCompliance.builder().build())
                .bankingDetails(BankingDetails.builder().build())
                .adminDetails(AdminDetails.builder()
                        .primary(AdminPrimary.builder().build())
                        .technical(AdminTechnical.builder().build())
                        .build())
                .build();

        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () ->
                customerService.saveCustomerRequest(request)
        );
        assertEquals("Required field 'industryType' cannot be empty", exception.getMessage());
    }
    @Test
    void testSaveCustomerRequest_NullCountry_ThrowsException() {
        CustomerRequest request = CustomerRequest.builder()
                .customerProfile(CustomerProfile.builder()
                        .customerName(CUSTOMER_NAME)
                        .industryType(INDUSTRY_TYPE)
                        .country(null)
                        .registeredAddress(Address.builder().build())
                        .build())
                .legalAndTaxCompliance(LegalAndTaxCompliance.builder().build())
                .bankingDetails(BankingDetails.builder().build())
                .adminDetails(AdminDetails.builder()
                        .primary(AdminPrimary.builder().build())
                        .technical(AdminTechnical.builder().build())
                        .build())
                .build();

        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () ->
                customerService.saveCustomerRequest(request)
        );
        assertEquals("Required field 'country' cannot be null", exception.getMessage());
    }
    @Test
    void testSaveCustomerRequest_NullBankingDetails_ThrowsException() {
        CustomerRequest request = CustomerRequest.builder()
                .customerProfile(CustomerProfile.builder()
                        .customerName(CUSTOMER_NAME)
                        .industryType(INDUSTRY_TYPE)
                        .registeredAddress(Address.builder().build())
                        .build())
                .legalAndTaxCompliance(LegalAndTaxCompliance.builder().build())
                .bankingDetails(null)
                .build();

        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () ->
                customerService.saveCustomerRequest(request)
        );
        assertEquals("Required field 'country' cannot be null", exception.getMessage());
    }
    @Test
    void testSaveCustomerRequest_NullAdminDetails_ThrowsException() {
        CustomerRequest request = CustomerRequest.builder()
                .customerProfile(CustomerProfile.builder()
                        .customerName(CUSTOMER_NAME)
                        .industryType(INDUSTRY_TYPE)
                        .country("India")
                        .registeredAddress(Address.builder().build())
                        .build())
                .legalAndTaxCompliance(LegalAndTaxCompliance.builder().build())
                .bankingDetails(BankingDetails.builder().build())
                .adminDetails(null)
                .build();

        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () ->
                customerService.saveCustomerRequest(request)
        );
        assertEquals("Required field 'adminDetails' cannot be null", exception.getMessage());
    }
    @Test
    void testSaveCustomerRequest_NullAdminPrimary_ThrowsException() {
        CustomerRequest request = CustomerRequest.builder()
                .customerProfile(CustomerProfile.builder()
                        .customerName(CUSTOMER_NAME)
                        .industryType(INDUSTRY_TYPE)
                        .country("India")
                        .registeredAddress(Address.builder().build())
                        .build())
                .legalAndTaxCompliance(LegalAndTaxCompliance.builder().build())
                .bankingDetails(BankingDetails.builder().build())
                .adminDetails(AdminDetails.builder()
                        .primary(null)
                        .technical(AdminTechnical.builder().build())
                        .build())
                .build();

        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () ->
                customerService.saveCustomerRequest(request)
        );
        assertEquals("Required field 'adminDetails.primary' cannot be null", exception.getMessage());
    }
    @Test
    void testSaveCustomerRequest_NullAdminTechnical_ThrowsException() {
        CustomerRequest request = CustomerRequest.builder()
                .customerProfile(CustomerProfile.builder()
                        .customerName(CUSTOMER_NAME)
                        .industryType(INDUSTRY_TYPE)
                        .country("India")
                        .registeredAddress(Address.builder().build())
                        .build())
                .legalAndTaxCompliance(LegalAndTaxCompliance.builder().build())
                .bankingDetails(BankingDetails.builder().build())
                .adminDetails(AdminDetails.builder()
                        .primary(AdminPrimary.builder().build())
                        .technical(null)
                        .build())
                .build();

        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () ->
                customerService.saveCustomerRequest(request)
        );
        assertEquals("Required field 'adminDetails.technical' cannot be null", exception.getMessage());
    }
//    @Test
//    void testSaveApiKey_Success() {
//        CustomerApiKeySchema apiKey = new CustomerApiKeySchema();
//        apiKey.setApiKey(ServiceImplTestConstants.Apikey);
//        apiKey.setCustomerEmail(ServiceImplTestConstants.ApiCustomerMail);
//
//
//        when(apiKeyRepository.save(apiKey)).thenReturn(apiKey);
//
//        CustomerResponse response = customerService.saveApiKey(apiKey);
//
//        assertNotNull(response);
//        assertEquals("API Key stored successfully", response.getMessage());
//        verify(apiKeyRepository, times(1)).save(apiKey);
//    }
    @Test
    void testSaveApiKey_Failure_NullApiKey() {
        CustomerApiKeySchema apiKey = new CustomerApiKeySchema();
        apiKey.setApiKey(null);
        apiKey.setCustomerEmail(ServiceImplTestConstants.ApiCustomerMail);

        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () -> {
            customerService.saveApiKey(apiKey);
        });

        assertEquals("API Key cannot be null or empty", exception.getMessage());
    }
    @Test
    void testSaveApiKey_Failure_EmptyEmail() {
        CustomerApiKeySchema apiKey = new CustomerApiKeySchema();
        apiKey.setApiKey(ServiceImplTestConstants.Apikey);
        apiKey.setCustomerEmail("");

        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () -> {
            customerService.saveApiKey(apiKey);
        });

        assertEquals("Customer email cannot be null or empty", exception.getMessage());
    }
}
