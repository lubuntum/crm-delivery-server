package com.delivery.mydelivery.controllers;

import com.delivery.mydelivery.annotation.accountmanage.HasRole;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.organization.OrganizationService;
import com.delivery.mydelivery.dto.organization.OrganizationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    OrganizationService organizationService;
    @Autowired
    AccountService accountService;

    @HasRole(RoleEnum.ADMIN)
    @PostMapping("/organization/create")
    public ResponseEntity<Boolean> createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        if (!accountService.isEmailValid(organizationDTO.getDirectorData().getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
        organizationService.createOrganization(organizationDTO);
        accountService.createAccount(organizationDTO.getDirectorData());
        return ResponseEntity.ok(true);
    }
    @HasRole(RoleEnum.ADMIN)
    @PostMapping("/organization/change-status")
    public ResponseEntity<Void> changeOrganizationActiveStatus(@RequestBody OrganizationDTO organizationDTO) {
        organizationService.changeOrganizationActiveStatus(organizationDTO);
        return ResponseEntity.ok().build();
    }
    @HasRole(RoleEnum.ADMIN)
    @GetMapping("/get-organizations")
    public ResponseEntity<List<OrganizationDTO>> getOrganizations() {
        return ResponseEntity.ok(organizationService.getAllOrganizations());
    }
}
