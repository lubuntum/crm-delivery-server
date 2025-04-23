package com.delivery.mydelivery.database.projections;

import java.math.BigDecimal;

public interface ItemProjection {
    Long getId();
    BigDecimal getPrice();
    BigDecimal getPricePerUnit();
    BigDecimal getAdditionalPrice();
    Double getSize();
    Double getWidth();
    Double getHeight();
    String getComment();
    Boolean getIsReady();
}
