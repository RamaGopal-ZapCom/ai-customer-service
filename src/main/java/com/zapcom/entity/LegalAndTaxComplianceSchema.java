package com.zapcom.entity;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class LegalAndTaxComplianceSchema {
    private String gstNumber;
    private String panCard;
    private String fssaiLicense;
    private String shopsAndEstablishmentLicense;
    private String tradeLicense;
}

