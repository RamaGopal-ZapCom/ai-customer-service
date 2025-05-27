package com.zapcom.model;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branding {
    @NotBlank
    private String logoUrl;
    @NotBlank
    private String brandName;
    @NotBlank
    private String greetingMessage;
    @NotBlank
    private String fallBackResponse;
}
