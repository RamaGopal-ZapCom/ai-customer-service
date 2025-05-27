package com.zapcom.model;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operations {
    private OperatingHours operatingHours;
    private List<String> cuisineType;
    private String deliveryRadiusPreference;
    @NotNull @Valid
    private Staff staff;
    private List< String> websiteOrSocialMediaLinks;
}
