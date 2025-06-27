package com.delivery.mydelivery.controllers.tgbot;

import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.organization.OrganizationService;
import com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO;
import com.delivery.mydelivery.services.jwt.JwtService;
import com.delivery.mydelivery.tgbot.TelegramBotService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tg-bot")
public class TgBotController {
    @Autowired
    TelegramBotService telegramBotService;
    @Autowired
    AccountService accountService;
    @Autowired
    JwtService jwtService;
    @PostMapping("/check-employees-requests")
    public ResponseEntity<Boolean> getActualMessages(){
        telegramBotService.checkEmployeesRequests();
        return ResponseEntity.ok(true);
    }
    @PostMapping("/send-notification")
    public ResponseEntity<Boolean> sendNotification(@RequestHeader("Authorization") String token,
                                                    @RequestBody ClientOrderDTO orderDTO) {
        telegramBotService.sendNotifications(
                accountService.getOrganizationByAccountId(Long.valueOf(jwtService.extractSubject(token))),
                orderDTO);
        return ResponseEntity.ok(true);
    }
}
