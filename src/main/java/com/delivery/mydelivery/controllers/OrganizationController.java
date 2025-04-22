package com.delivery.mydelivery.controllers;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.projections.MaterialProjection;
import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.database.services.productmanage.MaterialService;
import com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO;
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
    MaterialService materialService;
    @Autowired
    JwtService jwtService;
    //TODO may be get orders depend on token -> account.employee.organization.id
    // cos its hard image just send org id, where we can store and get.
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
}
