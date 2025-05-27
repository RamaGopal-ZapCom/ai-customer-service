package com.zapcom.entity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@Document
public class BankingDetailsSchema {
    private String bankAccountNumber;
    private String bankName;
    private String ifscCode;
    private String cancelledChequeOrBankStatement;
}

