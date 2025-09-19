package com.delivery.mydelivery.controllers.sms;

import com.delivery.mydelivery.annotation.accountmanage.HasRole;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.services.accountmanage.ClientService;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.services.smsprovider.SmsProvider;
import com.delivery.mydelivery.services.smsprovider.SmsResponse;
import com.delivery.mydelivery.services.smsprovider.smsaero.dto.SmsAeroResponse;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/sms")
public class SmsController {
    private final SmsProvider smsProvider;
    private final OrderService orderService;

    @HasRole(RoleEnum.ADMIN)
    @PostMapping("/broadcast")
    public ResponseEntity<SmsResponse> broadcastToCustomers(@RequestParam(required = true)LocalDateTime startDate,
                                                            @RequestParam(required = true) LocalDateTime endDate,
                                                            @RequestParam(required = true) Long organizationId,
                                                            @RequestParam(required = true) String messageTemplate){
        return ResponseEntity.ok(smsProvider.sendBulkSms(
                orderService.getCompletedOrdersBetweenDatesByOrganization(startDate, endDate, organizationId),
                messageTemplate));
    }

}
