package com.delivery.mydelivery.database.repositories.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.accountmanage.EmployeeWorkflow;
import com.delivery.mydelivery.database.projections.EmployeeWorkflowProjection;
import com.delivery.mydelivery.dto.accountmanage.EmployeeWorkflowDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeWorkflowRepository extends JpaRepository<EmployeeWorkflow, Long> {
    EmployeeWorkflowProjection findEmployeeWorkflowByIdAndWorkDate(Long id, LocalDate workDate);
    EmployeeWorkflow findEmployeeWorkflowByEmployeeAndWorkDate(Employee employee, LocalDate workDate);
    EmployeeWorkflowProjection findEmployeeWorkflowProjectionByEmployeeAndWorkDate(Employee employee, LocalDate workDate);

    @Query("SELECT new com.delivery.mydelivery.dto.accountmanage.EmployeeWorkflowDTO( " +
            "e_w.id, e.id, e_w.workDate, e_w.ordersCount, e_w.itemsArea, e_w.ordersPrice, " +
            "e.name, e.secondName, e.patronymic) " +
            "FROM EmployeeWorkflow e_w " +
            "LEFT JOIN e_w.employee e " +
            "WHERE e.organization.id = :organizationId AND e_w.workDate = :workDate")
    List<EmployeeWorkflowDTO> findEmployeesWorkflowDTOByOrganizationIdAndWorkDate(Long organizationId, LocalDate workDate);
}
