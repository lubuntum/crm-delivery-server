package com.delivery.mydelivery.controllers.accountmanage;

import com.delivery.mydelivery.annotation.accountmanage.HasRole;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.projections.EmployeeWorkflowProjection;
import com.delivery.mydelivery.database.services.accountmanage.EmployeeWorkflowService;
import com.delivery.mydelivery.dto.accountmanage.EmployeeWorkflowDTO;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    EmployeeWorkflowService employeeWorkflowService;
    @Autowired
    JwtService jwtService;
    @GetMapping("/employee/workflow-by-date")
    public ResponseEntity<EmployeeWorkflowProjection> getEmployeeWorkflowByDate(
            @RequestHeader("Authorization") String token,
            @RequestParam("workDate")LocalDate workDate){
        return ResponseEntity.ok(employeeWorkflowService.getEmployeeWorkflowByEmployeeIdAndWorkDate(
                Long.valueOf(jwtService.extractSubject(token)), workDate));
    }
    //add interceptor which checks role (only for couriers)
    //TODO remove this method, this data updates in FinishOrderService
    @HasRole(RoleEnum.COURIER)
    @PostMapping("/employee/update-workflow")
    public ResponseEntity<Boolean> updateEmployeeWorkflowByDate(
            @RequestHeader("Authorization") String token,
            @RequestBody EmployeeWorkflowDTO employeeWorkflowDTO) {
        employeeWorkflowDTO.setEmployeeId(Long.valueOf(jwtService.extractSubject(token)));
        return ResponseEntity.ok(employeeWorkflowService.updateEmployeeWorkflow(employeeWorkflowDTO));
    }
}
