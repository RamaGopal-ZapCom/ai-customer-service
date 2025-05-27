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
public class Theme {
    @NotBlank
    private String primaryColor;
    @NotBlank
    private String secondaryColor;
    @NotBlank
    private String fontStyle;
}
