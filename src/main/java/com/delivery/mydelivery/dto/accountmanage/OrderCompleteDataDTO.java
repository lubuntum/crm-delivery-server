package com.delivery.mydelivery.dto.accountmanage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * Using for update data in EmployeeWorkflow
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCompleteDataDTO {
    Long courierId;
    LocalDate workDate;
    Double itemsArea;// items area for one order
    BigDecimal totalPrice; // data for one order
}
