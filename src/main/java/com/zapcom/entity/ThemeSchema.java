package com.zapcom.entity;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ThemeSchema {
    private String primaryColor;
    private String secondaryColor;
    private String fontStyle;
}

