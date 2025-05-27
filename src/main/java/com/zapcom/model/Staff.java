package com.zapcom.model;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
    @NotNull @Min(0)
    private Integer kitchenStaff;
    @NotNull @Min(0)
    private Integer deliveryStaff;
}
