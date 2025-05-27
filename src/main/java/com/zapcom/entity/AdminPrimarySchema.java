package com.zapcom.entity;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class AdminPrimarySchema {
    private String name;
    private String phone;
    private String address;
    private String timeZone;
}

