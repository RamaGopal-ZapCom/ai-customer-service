package com.zapcom.controller;

import com.zapcom.entity.CustomerApiKeySchema;
import com.zapcom.model.ApiKeyRequest;
import com.zapcom.model.CustomerRequest;
import com.zapcom.model.JwtTokenRequest;
import com.zapcom.model.response.CustomerResponse;
import com.zapcom.model.response.CustomerUIResponse;
import com.zapcom.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/customers")
@Tag(name = "Customer API Endpoints",
        description = "APIs for handling customer registration, API key, and JWT token management")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;


    @PostMapping("/createCustomerProfile")
    @Operation(summary = "Register a new customer",
            description = "Stores all customer information received from the authentication service.")
    public ResponseEntity<?> createCustomerProfile(@Valid @RequestBody CustomerRequest customerRequest) {
        logger.info("Received request to create customer profile");
        CustomerResponse message = customerService.saveCustomerRequest(customerRequest);
        logger.info("Customer profile created successfully");
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    @PostMapping("/storeApiKey")
    @Operation(summary = "Store API Key",
            description = "Stores the API key for the customer.")
    public ResponseEntity<?> storeApiKey(@RequestBody ApiKeyRequest apiKeyRequest) {
        logger.info("Received request to store API key");
        CustomerApiKeySchema customerApiKeySchema = new CustomerApiKeySchema();
        customerApiKeySchema.setCustomerEmail(apiKeyRequest.getCustomerEmail());
        customerApiKeySchema.setApiKey(apiKeyRequest.getApiKey());

        CustomerResponse response = customerService.saveApiKey(customerApiKeySchema);
        logger.info("API key stored successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/storeJwtToken")
    @Operation(summary = "Store JWT Token",
            description = "Stores or updates the JWT token for a given email.")
    public ResponseEntity<?> storeJwtToken(@Valid @RequestBody JwtTokenRequest jwtTokenRequest) {
        logger.info("Received request to store JWT token");
        customerService.storeJwtToken(jwtTokenRequest);
        logger.info("JWT token stored/updated successfully for {}", jwtTokenRequest.getCustomerEmail());
        Map<String, String> response = new HashMap<>();
        response.put("message", "JWT Token is stored successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Get Customer Details",
            description = "Validates JWT Token and returns customer information.",
            security = {@io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "jwt")})
    @GetMapping("/getCustomer")
    public ResponseEntity<CustomerUIResponse> getCustomer(HttpServletRequest request) {
        logger.info("Received Get Customer request.");
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7).trim();

        CustomerUIResponse response = customerService.getCustomerDetails(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
