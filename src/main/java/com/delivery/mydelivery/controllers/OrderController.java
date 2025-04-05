package com.delivery.mydelivery.controllers;

import com.delivery.mydelivery.database.services.accountmanage.ClientService;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ClientService clientService;
    @PostMapping("/create-order")
    ResponseEntity<Long> createOrder(@RequestBody ClientOrderDTO clientOrderDTO){
        clientOrderDTO.setClientId(clientService.createClientIfNotExists(clientOrderDTO.extractClientData()));
        return ResponseEntity.ok(orderService.createOrder(clientOrderDTO));
    }
}
