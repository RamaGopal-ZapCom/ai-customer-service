package com.zapcom.entity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@Document
public class MetaDataSchema {
    private String note;
}

