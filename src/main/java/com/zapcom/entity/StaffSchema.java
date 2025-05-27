package com.zapcom.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class StaffSchema {
    private Integer deliveryStaff;
    private Integer kitchenStaff;
}


