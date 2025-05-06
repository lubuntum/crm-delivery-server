package com.delivery.mydelivery.database.projections;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EmployeeWorkflowProjection {
    Long getId();
    LocalDate getWorkDate();
    Integer getOrdersCount();
    Double getItemsArea();
    BigDecimal getOrdersPrice();
    LocalDateTime getLastUpdate();

}
