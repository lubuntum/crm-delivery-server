package com.delivery.mydelivery.controllers.accountmanage;

import com.delivery.mydelivery.annotation.accountmanage.HasRole;
import com.delivery.mydelivery.database.entities.accountmanage.Account;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.projections.EmployeeWorkflowProjection;
import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.accountmanage.EmployeeWorkflowService;
import com.delivery.mydelivery.database.services.organization.OrganizationService;
import com.delivery.mydelivery.dto.accountmanage.EmployeeWorkflowDTO;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    EmployeeWorkflowService employeeWorkflowService;
    @Autowired
    AccountService accountService;
    @Autowired
    JwtService jwtService;
    @GetMapping("/employee/workflow-by-date")
    public ResponseEntity<EmployeeWorkflowProjection> getEmployeeWorkflowByDate(
            @RequestHeader("Authorization") String token,
            @RequestParam("workDate")LocalDate workDate){
        return ResponseEntity.ok(employeeWorkflowService.getEmployeeWorkflowByEmployeeIdAndWorkDate(
                accountService.getEmployeeByAccountId(Long.valueOf(jwtService.extractSubject(token))).getId(),
                workDate));
    }
    @GetMapping("/workflow-by-organization-and-date")
    public ResponseEntity<List<EmployeeWorkflowDTO>> getEmployeesWorkFlowByDateAndOrganization(
            @RequestHeader("Authorization") String token,
            @RequestParam("workDate") LocalDate workDate){
        return ResponseEntity.ok(employeeWorkflowService.getEmployeesWorkFlowByOrganizationAndWorkDate(
                accountService.getOrganizationByAccountId(Long.valueOf(jwtService.extractSubject(token))).getId(),
                workDate
        ));
    }
    //add interceptor which checks role (only for couriers)
    // remove this method, this data updates in FinishOrderService
    @Deprecated
    @HasRole(RoleEnum.COURIER)
    @PostMapping("/employee/update-workflow")
    public ResponseEntity<Boolean> updateEmployeeWorkflowByDate(
            @RequestHeader("Authorization") String token,
            @RequestBody EmployeeWorkflowDTO employeeWorkflowDTO) {
        //TODO mistake, should get employee id by account but not just extract token (there is accountId)
        employeeWorkflowDTO.setEmployeeId(accountService.getEmployeeByAccountId(Long.valueOf(jwtService.extractSubject(token))).getId());
        return ResponseEntity.ok(employeeWorkflowService.updateEmployeeWorkflow(employeeWorkflowDTO));
    }
}
