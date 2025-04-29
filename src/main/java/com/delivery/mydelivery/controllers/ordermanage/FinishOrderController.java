package com.delivery.mydelivery.controllers.ordermanage;

import com.delivery.mydelivery.dto.ordermanage.OrderFinishDTO;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders-finish")
public class FinishOrderController {
    @Autowired
    JwtService jwtService;
    @PostMapping("/create")
    public ResponseEntity<Long> createOrderFinish(@RequestHeader("Authorization") String token,
                                                  @RequestBody OrderFinishDTO orderFinishDTO) {
        return null;// TODO make repo and service save it return id
    }
}
