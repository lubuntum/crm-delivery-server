package com.delivery.mydelivery.controllers;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {
    @Autowired
    OrderService orderService;
    @GetMapping("/orders")
    public ResponseEntity<List<ClientOrderDTO>> getOrdersByOrganizationId(@RequestParam Long organizationId) {
        return ResponseEntity.ok(orderService.getAllOrdersByOrganizationId(organizationId));
    }
}
