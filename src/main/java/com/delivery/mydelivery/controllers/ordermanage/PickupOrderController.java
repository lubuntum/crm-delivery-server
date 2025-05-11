package com.delivery.mydelivery.controllers.ordermanage;

import com.delivery.mydelivery.database.projections.OrderPickupProjection;
import com.delivery.mydelivery.database.services.ordermanage.OrderPickupService;
import com.delivery.mydelivery.dto.ordermanage.orderpickup.OrderPickupDTO;
import com.delivery.mydelivery.services.jwt.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/orders-pickup")
public class PickupOrderController {
    @Autowired
    private OrderPickupService orderPickupService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ObjectMapper objectMapper;
    @PostMapping("/create")
    public ResponseEntity<Long> createOrderPickup(
            @RequestHeader("Authorization") String token,
            @RequestParam("orderPickup") String orderPickupStr,
            @RequestParam(value = "images", required = false)List<MultipartFile> images) throws JsonProcessingException {
        //TODO think, may be logic of extraction token data transfer to
        // interceptor which append request with extracted data
        OrderPickupDTO orderPickupDTO = objectMapper.readValue(orderPickupStr, OrderPickupDTO.class);
        orderPickupDTO.setImagesTemp(images);
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
