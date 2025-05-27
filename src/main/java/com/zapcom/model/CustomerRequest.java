package com.zapcom.model;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    @NotNull @Valid
    private CustomerProfile customerProfile;
    private LegalAndTaxCompliance legalAndTaxCompliance;
    private BankingDetails bankingDetails;
    @NotNull @Valid
    private AdminDetails adminDetails;
    private MetaData metaData;
    @NotNull @Valid
    private Operations operations;
    @NotNull @Valid
    private ApiConfiguration apiConfiguration;
    @NotNull @Valid
    private Branding branding;
    @NotNull @Valid
    private ChatbotConfig chatbotConfig;
    @NotNull @Valid
    private Agreements agreements;
}
