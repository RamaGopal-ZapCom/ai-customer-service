package com.zapcom.service;

import com.zapcom.entity.CustomerApiKeySchema;
import com.zapcom.model.CustomerRequest;
import com.zapcom.model.JwtTokenRequest;
import com.zapcom.model.response.CustomerResponse;
import com.zapcom.model.response.CustomerUIResponse;

public interface CustomerService {
    CustomerResponse saveCustomerRequest(CustomerRequest customerRequest);
    CustomerResponse saveApiKey(CustomerApiKeySchema customerApiKeySchema);

    void storeJwtToken(JwtTokenRequest request);

    CustomerUIResponse getCustomerDetails(String jwtToken);

}



