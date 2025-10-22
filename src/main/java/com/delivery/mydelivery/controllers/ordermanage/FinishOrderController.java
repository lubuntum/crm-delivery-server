package com.delivery.mydelivery.controllers.ordermanage;

import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.ordermanage.OrderFinishService;
import com.delivery.mydelivery.dto.ordermanage.OrderFinishDTO;
import com.delivery.mydelivery.services.documents.autofill.services.CompletionWorkFillerService;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/orders-finish")
public class FinishOrderController {
    @Autowired
    JwtService jwtService;
    @Autowired
    OrderFinishService orderFinishService;
    @Autowired
    AccountService accountService;
    @Autowired
    CompletionWorkFillerService completionWorkFillerService;
    @PostMapping("/create")
    public ResponseEntity<Long> createOrderFinish(@RequestHeader("Authorization") String token,
                                                  @RequestBody OrderFinishDTO orderFinishDTO) throws IOException {
        orderFinishDTO.setCourierId(
                accountService.getEmployeeByAccountId(
                        Long.valueOf(jwtService.extractSubject(token))).getId());
        completionWorkFillerService.createCompletionWork(orderFinishDTO);
        //orderFinishDTO.getOrderCompleteData().setCourierId(Long.valueOf(jwtService.extractSubject(token)));
        return ResponseEntity.ok(orderFinishService.createOrderFinish(orderFinishDTO));
    }
    @GetMapping("/order-finish/by-order-id/{orderId}")
    public ResponseEntity<OrderFinishDTO> getOrderFinishByOrderId(@PathVariable("orderId") Long orderId){
        return ResponseEntity.ok(orderFinishService.getOrderFinishByOrderId(orderId));
    }
    @GetMapping("/by-orders-id")
    public ResponseEntity<List<OrderFinishDTO>> getOrderFinishListByOrdersId(@RequestParam List<Long> ordersId) {
        return ResponseEntity.ok(orderFinishService.getOrderFinishListByOrdersId(ordersId));
    }
}
