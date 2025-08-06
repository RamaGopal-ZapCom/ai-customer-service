package com.zapcom.entity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@Document
public class ChatbotConfigSchema {
    private ThemeSchema theme;
    private String supportedLanguages;
    private String chatWidgetPosition;
}
