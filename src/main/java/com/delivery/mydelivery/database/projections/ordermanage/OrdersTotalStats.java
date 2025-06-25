package com.delivery.mydelivery.database.projections.ordermanage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrdersTotalStats {
    private Double totalSize;
    private BigDecimal totalPrice;
    private Long ordersCount;

    public OrdersTotalStats(Double totalSize, BigDecimal totalPrice, Long ordersCount) {
        this.totalSize = totalSize;
        this.totalPrice = totalPrice;
        this.ordersCount = ordersCount;
    }
}
