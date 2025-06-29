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
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@RestController
@RequestMapping("/api/tg-bot")
public class TgBotController {
    @Autowired
    TelegramBotService telegramBotService;
    @Autowired
    AccountService accountService;
    @Autowired
    JwtService jwtService;
    @PostMapping("/subscribe-notifications")
    public ResponseEntity<Boolean> getActualMessages(@RequestHeader("Authorization") String token){
        List<Update> updates = telegramBotService.getUpdates(0);
        telegramBotService.checkEmployeesRequests(updates);
        return ResponseEntity.ok(telegramBotService.isEmployeeInUpdates(
                Long.valueOf(jwtService.extractSubject(token)),
                updates
        ));
    }
    @PostMapping("/unsubscribe-notifications")
    public ResponseEntity<Boolean> unsubscribeNotifications(@RequestHeader("Authorization") String token) {
        telegramBotService.unsubscribeNotifications(Long.valueOf(jwtService.extractSubject(token)));
        return ResponseEntity.ok(true);
    }
    @PostMapping("/send-notification")
    public ResponseEntity<Boolean> sendNotification(@RequestHeader("Authorization") String token,
                                                    @RequestBody ClientOrderDTO orderDTO) {
        orderDTO.setOrganizationId(
                accountService.getOrganizationByAccountId(Long.valueOf(jwtService.extractSubject(token))).getId());
        telegramBotService.sendNotifications(orderDTO);
        return ResponseEntity.ok(true);
    }

}
