package com.zapcom.model;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfile {
        @NotBlank
        private String customerName;
        @NotBlank
        private String industryType;
        @NotBlank
        private String businessCategory;
        @NotNull @Valid
        private Address registeredAddress;
        @NotBlank
        private String country;
        @NotBlank
        private String gstNumber;
        @NotBlank
        private String customerWebsite;
        @NotBlank
        private String customerRegistrationNumber;
        private String ownerName;
        private String contactNumber;
        @Email @NotBlank
        private String customerEmail;
}


