package com.delivery.mydelivery.database.projections.ordermanage;

import java.time.LocalDateTime;

public interface OrderPickupProjection {
    Long getId();
    LocalDateTime getTakenAt();
    String getComment();
    Integer getItemsCount();
}
