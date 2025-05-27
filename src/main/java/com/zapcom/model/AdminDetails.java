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
public class AdminDetails {
    @NotNull @Valid
    private AdminPrimary primary;
    @NotNull @Valid
    private AdminTechnical technical;
}
