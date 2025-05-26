package com.delivery.mydelivery.controllers;

import com.delivery.mydelivery.annotation.accountmanage.HasRole;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.projections.MaterialProjection;
import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.database.services.organization.OrganizationService;
import com.delivery.mydelivery.database.services.productmanage.MaterialService;
import com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO;
import com.delivery.mydelivery.dto.organization.OrganizationDTO;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {
    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    MaterialService materialService;
    @Autowired
    JwtService jwtService;
    @GetMapping("/orders")
    public ResponseEntity<List<ClientOrderDTO>> getOrdersByOrganizationId(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(orderService.getAllOrdersByOrganizationId(
                accountService.getOrganizationByAccountId(
                        Long.valueOf(jwtService.extractSubject(token))).getId()));
    }
    @GetMapping("/materials")
    public ResponseEntity<List<MaterialProjection>> getMaterialsByOrganization(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(materialService.getMaterialsByOrganizationId(
                accountService.getOrganizationByAccountId(Long.valueOf(jwtService.extractSubject(token))).getId()));
    }
    //TODO think how to create presets for materials list for organization
    @HasRole(RoleEnum.ADMIN)
    @PostMapping("/create")
    public ResponseEntity<Boolean> createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        organizationService.createOrganization(organizationDTO);
        accountService.createAccount(organizationDTO.getDirectorData());
        return ResponseEntity.ok(true);
    }
}
