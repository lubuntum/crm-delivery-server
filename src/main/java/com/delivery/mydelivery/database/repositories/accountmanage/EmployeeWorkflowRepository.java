package com.delivery.mydelivery.database.repositories.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.accountmanage.EmployeeWorkflow;
import com.delivery.mydelivery.database.projections.EmployeeWorkflowProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EmployeeWorkflowRepository extends JpaRepository<EmployeeWorkflow, Long> {
    EmployeeWorkflowProjection findEmployeeWorkflowByIdAndWorkDate(Long id, LocalDate workDate);
    EmployeeWorkflow findEmployeeWorkflowByEmployeeAndWorkDate(Employee employee, LocalDate workDate);
    EmployeeWorkflowProjection findEmployeeWorkflowProjectionByEmployeeAndWorkDate(Employee employee, LocalDate workDate);
}
