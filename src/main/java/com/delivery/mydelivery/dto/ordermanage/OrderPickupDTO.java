package com.delivery.mydelivery.dto.ordermanage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPickupDTO {
    private Long id;
    private LocalDateTime pickedAt;
    private String comment;
    private Integer itemsCount;
    private Long orderId;
    private Long courierId;
}
