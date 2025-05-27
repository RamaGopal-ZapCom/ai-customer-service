package com.zapcom.controller;
import com.zapcom.entity.CustomerApiKeySchema;
import com.zapcom.model.ApiKeyRequest;
import com.zapcom.model.CustomerRequest;
import com.zapcom.model.response.CustomerResponse;
import com.zapcom.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/customers")
@Tag(name = "Customer API Endpoints", description = "APIs for handling customer registration and API key management")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/createCustomerProfile")
    @Operation(
            summary = "Register a new customer",
            description = "Stores all customer information received from the authentication service."
    )
    public ResponseEntity<?> createCustomerProfile(
            @Parameter(description = "Full customer data") @Valid @RequestBody CustomerRequest customerRequest
    ) {
        CustomerResponse message = customerService.saveCustomerRequest(customerRequest);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
    @PostMapping("/storeApiKey")
    @Operation(
            summary = "Store API Key",
            description = "Stores the API key provided by the authentication service for the customer."
    )
    public ResponseEntity<?> storeApiKey(
            @Parameter(description = "API Key information") @RequestBody ApiKeyRequest apiKeyRequest
    ) {
        CustomerApiKeySchema customerApiKeySchema = new CustomerApiKeySchema();
        customerApiKeySchema.setCustomerEmail(apiKeyRequest.getCustomerEmail());
        customerApiKeySchema.setApiKey(apiKeyRequest.getApiKey());
          CustomerResponse response = customerService.saveApiKey(customerApiKeySchema);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
