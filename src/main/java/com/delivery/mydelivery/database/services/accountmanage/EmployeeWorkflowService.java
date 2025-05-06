package com.delivery.mydelivery.database.services.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.accountmanage.EmployeeWorkflow;
import com.delivery.mydelivery.database.projections.EmployeeWorkflowProjection;
import com.delivery.mydelivery.database.repositories.accountmanage.EmployeeWorkflowRepository;
import com.delivery.mydelivery.dto.EmployeeWorkflowDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class EmployeeWorkflowService {
    @Autowired
    EmployeeWorkflowRepository employeeWorkflowRepository;
    @Autowired
    EmployeeService employeeService;
    //TODO write a method which find workflow by employeeId and workDate
    public EmployeeWorkflowProjection getEmployeeWorkflowByIdAndWorkDate(Long id, LocalDate workDate){
        return employeeWorkflowRepository.findEmployeeWorkflowByIdAndWorkDate(id, workDate);
    }
    public boolean updateEmployeeWorkflow(EmployeeWorkflowDTO employeeWorkflowDTO) {
        Employee employee = employeeService.getEmployeeById(employeeWorkflowDTO.getEmployeeId());
        EmployeeWorkflow employeeWorkflow = employeeWorkflowRepository
                .findEmployeeWorkflowByEmployeeAndWorkDate(employee, employeeWorkflowDTO.getWorkDate());
        if (employeeWorkflow == null) {
            employeeWorkflow = new EmployeeWorkflow();
            employeeWorkflow.setItemsArea(employeeWorkflowDTO.getItemsArea());
            employeeWorkflow.setOrdersPrice(employeeWorkflowDTO.getOrdersPrice());
            employeeWorkflow.setOrdersCount(1);
            employeeWorkflow.setWorkDate(employeeWorkflowDTO.getWorkDate());
            employeeWorkflow.setEmployee(employee);
        } else {
            employeeWorkflow.setItemsArea(employeeWorkflow.getItemsArea() + employeeWorkflowDTO.getItemsArea());
            employeeWorkflow.setOrdersPrice(employeeWorkflow.getOrdersPrice().add(employeeWorkflowDTO.getOrdersPrice()));
            employeeWorkflow.setOrdersCount(employeeWorkflow.getOrdersCount() + 1);
        }
        return employeeWorkflowRepository.save(employeeWorkflow).getId() != null;
    }
}
