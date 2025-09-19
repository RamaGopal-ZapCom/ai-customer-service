package com.zapcom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zapcom.constants.ControllerTestConstants;
import com.zapcom.entity.CustomerApiKeySchema;
import com.zapcom.model.*;
import com.zapcom.model.response.CustomerResponse;
import com.zapcom.repository.ApiKeyRepository;
import com.zapcom.repository.CustomerServiceRepository;
import com.zapcom.repository.JwtTokenRepository;
import com.zapcom.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerService customerService;


  @MockBean
  private ApiKeyRepository apiKeyRepository;

  @MockBean
  private JwtTokenRepository jwtTokenRepository;

  @MockBean
  private CustomerServiceRepository customerServiceRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testCreateCustomer_success() throws Exception {
    // Build CustomerRequest
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
            .gstNumber(null).panCard(null).fssaiLicense(null)
            .shopsAndEstablishmentLicense(null).tradeLicense(null)
            .build();

    BankingDetails banking = BankingDetails.builder()
            .bankAccountNumber(null).bankName(null).ifscCode(null).cancelledChequeOrBankStatement(null).build();

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
                    .openingTime(null).closingTime(null).build())
            .cuisineType(Collections.singletonList(""))
            .deliveryRadiusPreference(null)
            .staff(Staff.builder().kitchenStaff(0).deliveryStaff(0).build())
            .websiteOrSocialMediaLinks(Collections.singletonList(""))
            .build();

    ApiConfiguration api = ApiConfiguration.builder()
            .estimatedMonthlyRequests(ControllerTestConstants.ESTIMATED_REQUESTS)
            .requestsPerMinute(ControllerTestConstants.REQUESTS_PER_MIN)
            .peakUsageHours(ControllerTestConstants.PEAK_HOURS)
            .botPurpose(ControllerTestConstants.BOT_PURPOSE)
            .complianceStandards(Arrays.asList(
                    ControllerTestConstants.COMPLIANCE_1,
                    ControllerTestConstants.COMPLIANCE_2,
                    ControllerTestConstants.COMPLIANCE_3))
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
            .termsAccepted(true).privacyPolicyAccepted(true).build();

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

    CustomerResponse mockResponse = CustomerResponse.builder()
            .message(ControllerTestConstants.Message)
            .build();

    Mockito.when(customerService.saveCustomerRequest(any(CustomerRequest.class)))
            .thenReturn(mockResponse);

    mockMvc.perform(post(ControllerTestConstants.CustomerUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("Customer created successfully"));
  }

  @Test
  public void testStoreApiKey_success() throws Exception {
    ApiKeyRequest request = new ApiKeyRequest();
    request.setCustomerEmail(ControllerTestConstants.ApiCustomerMail);
    request.setApiKey(ControllerTestConstants.APIkeY);

    CustomerResponse mockResponse = CustomerResponse.builder()
            .message(ControllerTestConstants.ApiMessage)
            .build();

    Mockito.when(customerService.saveApiKey(any(CustomerApiKeySchema.class)))
            .thenReturn(mockResponse);

    mockMvc.perform(post(ControllerTestConstants.apikeyUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("API Key stored successfully"));
  }

  @Test
  public void testCreateCustomer_invalidRequest() throws Exception {
    CustomerRequest request = new CustomerRequest();

    mockMvc.perform(post(ControllerTestConstants.CustomerUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
  }
}
