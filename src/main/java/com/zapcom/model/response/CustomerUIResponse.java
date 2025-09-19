package com.zapcom.model.response;


import com.zapcom.model.AdminDetails;
import com.zapcom.model.Agreements;
import com.zapcom.model.ApiConfiguration;
import com.zapcom.model.BankingDetails;
import com.zapcom.model.Branding;
import com.zapcom.model.ChatbotConfig;
import com.zapcom.model.CustomerProfile;
import com.zapcom.model.LegalAndTaxCompliance;
import com.zapcom.model.MetaData;
import com.zapcom.model.Operations;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUIResponse {


    private CustomerProfile customerProfile;
    private LegalAndTaxCompliance legalAndTaxCompliance;
    private BankingDetails bankingDetails;
    private AdminDetails adminDetails;
    private MetaData metaData;
    private Operations operations;
    private ApiConfiguration apiConfiguration;
    private Branding branding;
    private ChatbotConfig chatbotConfig;
    private Agreements agreements;
}

