package com.delivery.mydelivery.dto.ordermanage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInspectionDTO {
    Long id;
    Long orderId;
    Long inspectorId;
    Integer itemsCount;
    LocalDateTime inspectedAt;
}
