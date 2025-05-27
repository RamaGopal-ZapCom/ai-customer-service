package com.zapcom.service;
import com.zapcom.entity.CustomerApiKeySchema;
import com.zapcom.model.CustomerRequest;
import com.zapcom.model.response.CustomerResponse;

public interface CustomerService {
    CustomerResponse saveCustomerRequest(CustomerRequest customerRequest);
    CustomerResponse saveApiKey(CustomerApiKeySchema customerApiKeySchema);
}


