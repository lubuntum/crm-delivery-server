package com.delivery.mydelivery.controllers.ordermanage;

import com.delivery.mydelivery.database.projections.OrderPickupProjection;
import com.delivery.mydelivery.database.services.ordermanage.OrderPickupService;
import com.delivery.mydelivery.dto.ordermanage.OrderPickupDTO;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders-pickup")
public class PickupOrderController {
    @Autowired
    private OrderPickupService orderPickupService;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/create")
    public ResponseEntity<Long> createOrderPickup(
            @RequestHeader("Authorization") String token,
            @RequestBody OrderPickupDTO orderPickupDTO){
        //TODO think, may be logic of extraction token data transfer to
        // interceptor which append request with extracted data
        orderPickupDTO.setCourierId(Long.valueOf(jwtService.extractSubject(token)));
        return ResponseEntity.ok(orderPickupService.createOrderPickup(orderPickupDTO));
    }
    @GetMapping("/order-pickup/{id}")
    public ResponseEntity<OrderPickupProjection> getOrderPickupById(@PathVariable Long id){
        return ResponseEntity.ok(orderPickupService.getOrderPickupById(id));
    }
    @GetMapping("/order-pickup/by-order-id/{orderId}")
    public ResponseEntity<OrderPickupDTO> getOrderPickupByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderPickupService.getOrderPickupByOrderId(orderId));
    }
}
