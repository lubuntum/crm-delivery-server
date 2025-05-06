package com.delivery.mydelivery.database.entities.accountmanage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_workflow")
public class EmployeeWorkflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "work_date")
    private LocalDate workDate;
    @Column(name = "orders_count")
    private Integer ordersCount;
    @Column(name = "items_area")
    private Double itemsArea;
    @Column(name = "orders_price")
    private BigDecimal ordersPrice;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @PrePersist
    protected void onCreate() {
        if (workDate == null) workDate = LocalDate.now();
        lastUpdate = LocalDateTime.now();
    }

}
