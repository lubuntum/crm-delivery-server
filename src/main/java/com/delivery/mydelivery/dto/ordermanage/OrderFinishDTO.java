package com.delivery.mydelivery.dto.ordermanage;

import com.delivery.mydelivery.database.entities.ordermanage.payment.PaymentMethodEnum;
import com.delivery.mydelivery.dto.accountmanage.OrderCompleteDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private OrderCompleteDataDTO orderCompleteData;
    private BigDecimal tips;
    private BigDecimal deliveryPrice;
}
