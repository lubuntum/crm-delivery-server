package com.delivery.mydelivery.controllers.ordermanage;

import com.delivery.mydelivery.database.services.ordermanage.OrderInspectionService;
import com.delivery.mydelivery.dto.ordermanage.OrderInspectionDTO;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders-inspection")
public class InspectionOrderController {
    @Autowired
    JwtService jwtService;
    @Autowired
    OrderInspectionService orderInspectionService;
    @PostMapping("/create")
    public ResponseEntity<Long> createOrderInspection(@RequestHeader("Authorization") String token,
                                                      @RequestBody OrderInspectionDTO orderInspectionDTO) {
        orderInspectionDTO.setInspectorId(Long.valueOf(jwtService.extractSubject(token)));
        return ResponseEntity.ok(orderInspectionService.createOrderInspection(orderInspectionDTO));
    }
    @GetMapping("/order-inspection/by-order-id/{orderId}")
    public ResponseEntity<OrderInspectionDTO> getOrderInspectionByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderInspectionService.getOrderInspectionDTO(orderId));
    }
}
