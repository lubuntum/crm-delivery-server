package com.delivery.mydelivery.dto.ordermanage;

import com.delivery.mydelivery.database.entities.ordermanage.status.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDTO {
    private Long orderId;
    private StatusEnum status;
}
