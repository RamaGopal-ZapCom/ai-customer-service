package com.zapcom.entity;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class AddressSchema {
    private String street;
    private String city;
    private String state;
    private String pinCode;
}

