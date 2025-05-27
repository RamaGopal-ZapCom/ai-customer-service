package com.zapcom.model;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agreements {
    @AssertTrue
    private boolean termsAccepted;
    @AssertTrue
    private boolean privacyPolicyAccepted;
}
