package com.zapcom.entity;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@Document
public class ChatbotConfigSchema {
    private ThemeSchema theme;
    private List<String> supportedLanguages;
    private String chatWidgetPosition;
}
