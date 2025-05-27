package com.zapcom.model;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class ApiConfiguration {
    @NotNull @Min(1)
    private Integer estimatedMonthlyRequests;
    @NotNull @Min(1)
    private Integer requestsPerMinute;
    @NotBlank
    private String peakUsageHours;
    @NotBlank
    private String botPurpose;
    @NotEmpty
    private List<@NotBlank String> complianceStandards;
}
