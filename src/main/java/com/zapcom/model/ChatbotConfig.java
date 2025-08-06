package com.zapcom.model;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatbotConfig {
    @NotNull @Valid
    private Theme theme;
    @NotEmpty @NotBlank
    private String supportedLanguages;
    @NotBlank
    private String chatWidgetPosition;
}
