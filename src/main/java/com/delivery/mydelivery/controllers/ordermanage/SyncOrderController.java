package com.delivery.mydelivery.controllers.ordermanage;

import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.ordermanage.OrderFinishService;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.dto.ordermanage.OrderFinishDTO;
import com.delivery.mydelivery.dto.ordermanage.OrderStatusDTO;
import com.delivery.mydelivery.services.documents.autofill.services.CompletionWorkFillerService;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
/*TODO
*  3. now check why is images not sending, then after sync data remove it and THEN load it and save it +
*  2. check why orderpickup empty after data sync +
*  1. local storage save data error now clear storage from old data before this is a FIX +
*  4. Remove old data after sync, load it and save again (pending...)
* */

@RestController
@RequestMapping("/api/sync")
public class SyncOrderController {
    private final OrderService orderService;
    private final OrderFinishService orderFinishService;
    private final JwtService jwtService;
    private final AccountService accountService;
    private final CompletionWorkFillerService completionWorkFillerService;

    public SyncOrderController(OrderService orderService, OrderFinishService orderFinishService, JwtService jwtService, AccountService accountService, CompletionWorkFillerService completionWorkFillerService) {
        this.orderService = orderService;
        this.orderFinishService = orderFinishService;
        this.jwtService = jwtService;
        this.accountService = accountService;
        this.completionWorkFillerService = completionWorkFillerService;
    }

    @PostMapping("/orders-statuses")
    public ResponseEntity<String> syncOrdersStatuses(@RequestBody List<OrderStatusDTO> orderStatusDTOS){
        try {
            orderService.changeOrdersStatuses(orderStatusDTOS);
            return ResponseEntity.ok("orders statuses sync");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/finish-orders")
    public ResponseEntity<String> syncOrderFinishData(@RequestHeader("Authorization") String token,
                                                          @RequestBody List<OrderFinishDTO> orderFinishDTOS) {
        try {
            Long accountId = Long.valueOf(jwtService.extractSubject(token));
            Long courierId = accountService.getEmployeeByAccountId(accountId).getId();
            orderFinishDTOS.forEach(dto -> dto.setCourierId(courierId));
            for (OrderFinishDTO dto: orderFinishDTOS) {
                try {
                    completionWorkFillerService.createCompletionWork(dto);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
            orderFinishService.createOrderFinishes(orderFinishDTOS);

            return ResponseEntity.ok("order complete data sync");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

