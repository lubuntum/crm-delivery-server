package com.delivery.mydelivery.dto.ordermanage;

import com.delivery.mydelivery.database.entities.ordermanage.payment.PaymentMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFinishDTO {
    private Long id;
    private Long orderId;
    private Long courierId;
    private String comment;
    private Integer itemsCount;
    private LocalDateTime deliveredAt;
    private PaymentMethodEnum paymentMethod;
}
