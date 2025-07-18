package com.delivery.mydelivery.database.services.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.accountmanage.EmployeeWorkflow;
import com.delivery.mydelivery.database.projections.EmployeeWorkflowProjection;
import com.delivery.mydelivery.database.repositories.accountmanage.EmployeeWorkflowRepository;
import com.delivery.mydelivery.dto.accountmanage.EmployeeWorkflowDTO;
import com.delivery.mydelivery.dto.accountmanage.OrderCompleteDataDTO;
import com.delivery.mydelivery.dto.ordermanage.OrderFinishDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeWorkflowService {
    @Autowired
    EmployeeWorkflowRepository employeeWorkflowRepository;
    @Autowired
    EmployeeService employeeService;

    public EmployeeWorkflowProjection getEmployeeWorkflowByEmployeeIdAndWorkDate(Long employeeId, LocalDate workDate) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return employeeWorkflowRepository.findEmployeeWorkflowProjectionByEmployeeAndWorkDate(employee, workDate);
    }
    public List<EmployeeWorkflowDTO> getEmployeesWorkFlowByOrganizationAndWorkDate(Long organizationId, LocalDate workDate){
        return employeeWorkflowRepository.findEmployeesWorkflowDTOByOrganizationIdAndWorkDate(organizationId, workDate);
    }
    public List<EmployeeWorkflowDTO> getEmployeesWorkFlowByOrganizationAndWorkDateBetween(Long organizationId, LocalDate startDate, LocalDate endDate) {
        return employeeWorkflowRepository.findEmployeesWorkflowDTOByOrganizationIdAndWorkDateBetween(organizationId, startDate, endDate);
    }
    //DEPRECATED not using by direct
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
    public void updateEmployeeWorkflow(OrderFinishDTO orderFinishDTO) {
        OrderCompleteDataDTO orderCompleteDataDTO = orderFinishDTO.getOrderCompleteData();

        Employee courier = employeeService.getEmployeeById(orderFinishDTO.getCourierId());
        EmployeeWorkflow employeeWorkflow = employeeWorkflowRepository
                .findEmployeeWorkflowByEmployeeAndWorkDate(courier, orderCompleteDataDTO.getWorkDate());
        if (employeeWorkflow == null) {
            employeeWorkflow = new EmployeeWorkflow();
            employeeWorkflow.setItemsArea(orderCompleteDataDTO.getItemsArea());
            employeeWorkflow.setOrdersPrice(orderCompleteDataDTO.getTotalPrice());
            employeeWorkflow.setOrdersCount(1);
            employeeWorkflow.setWorkDate(orderCompleteDataDTO.getWorkDate());
            employeeWorkflow.setEmployee(courier);
        } else {
            employeeWorkflow.setItemsArea(employeeWorkflow.getItemsArea() + orderCompleteDataDTO.getItemsArea());
            employeeWorkflow.setOrdersPrice(employeeWorkflow.getOrdersPrice().add(orderCompleteDataDTO.getTotalPrice()));
            employeeWorkflow.setOrdersCount(employeeWorkflow.getOrdersCount() + 1);
        }
        employeeWorkflowRepository.save(employeeWorkflow);
    }
}
