package com.delivery.mydelivery.dto.accountmanage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWorkflowDTO {
    Long id;
    Long employeeId;
    LocalDate workDate;
    Double itemsArea;//data for all items area in orders per day
    BigDecimal ordersPrice;//data for all orders prices per day
}
