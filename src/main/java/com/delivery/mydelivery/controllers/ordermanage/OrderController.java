package com.delivery.mydelivery.controllers.ordermanage;

import com.delivery.mydelivery.annotation.accountmanage.HasRole;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.ordermanage.status.StatusEnum;
import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.accountmanage.ClientService;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.database.services.productmanage.ItemService;
import com.delivery.mydelivery.dto.ordermanage.clientorder.ClientOrderDTO;
import com.delivery.mydelivery.dto.ordermanage.OrderStatusDTO;
import com.delivery.mydelivery.dto.ordermanage.clientorder.ClientOrderMapper;
import com.delivery.mydelivery.dto.productmanage.ItemDTO;
import com.delivery.mydelivery.dto.productmanage.ItemMapper;
import com.delivery.mydelivery.services.jwt.JwtService;
import com.delivery.mydelivery.tgbot.TelegramBotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TelegramBotService telegramBotService;
    @PostMapping("/create-order")
    ResponseEntity<Boolean> createOrder(@RequestHeader("Authorization") String token,
                                     @RequestBody ClientOrderDTO clientOrderDTO){
        clientOrderDTO.setClientId(clientService.createClientIfNotExists(clientOrderDTO.extractClientData()));
        clientOrderDTO.setOrganizationId(accountService.getOrganizationByAccountId(Long.valueOf(jwtService.extractSubject(token))).getId());
        //parse returned entity with serial number
        telegramBotService.sendNotifications(ClientOrderMapper.toDTO(orderService.createOrder(clientOrderDTO)));
        return ResponseEntity.ok(true);
    }
    @PatchMapping("/update-order")
    ResponseEntity<ClientOrderDTO> updateOrder(@RequestBody ClientOrderDTO clientOrderDTO){
        return ResponseEntity.ok(orderService.updateOrder(clientOrderDTO));
    }
    @PostMapping("/remove-order")
    ResponseEntity<String> removeOrder(@RequestBody ClientOrderDTO clientOrderDTO) {
        orderService.removeOrder(clientOrderDTO);
        return ResponseEntity.ok("Removed");
    }
    @PostMapping("/change-order-status")
    ResponseEntity<StatusEnum> changeOrderStatus(@RequestHeader("Authorization") String token,
                                                 @RequestBody OrderStatusDTO orderStatusDTO) {
        return ResponseEntity.ok(orderService.changeOrderStatus(orderStatusDTO));
    }
    @GetMapping("/order/{id}")
    ResponseEntity<ClientOrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getClientOrderDTOById(id));
    }
    @HasRole(RoleEnum.DIRECTOR)
    @GetMapping("/orders-between-dates")
    ResponseEntity<List<ClientOrderDTO>> getOrdersBetweenDates(@RequestHeader("Authorization") String token,
                                                      @RequestParam(required = true) LocalDateTime startDate,
                                                      @RequestParam(required = true) LocalDateTime endDate){
        return ResponseEntity.ok(orderService.getOrdersBetweenDatesForOrganization(
                startDate,
                endDate,
                accountService.getOrganizationByAccountId(Long.valueOf(jwtService.extractSubject(token))).getId()));

    }
    @PostMapping("/items/create-item")
    ResponseEntity<ItemDTO> createItemForOrder(@RequestParam("itemDTOJson") String itemDTOJson,
                                               @RequestParam(value = "images", required = false)List<MultipartFile> images) throws JsonProcessingException {
        ItemDTO itemDTO = objectMapper.readValue(itemDTOJson, ItemDTO.class);
        itemDTO.setImagesTemp(images);
        return ResponseEntity.ok(ItemMapper.toDTO(itemService.createItem(itemDTO)));
    }

    @DeleteMapping("/items/delete-item/{id}")
    ResponseEntity<Boolean> deleteItemFromOrder(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.deleteItem(id));
    }
    /**
     * Change item ready state.
     * Example - /items/item/change-ready-state?id=5&isReady=true
     * @id - current id for changing item
     * @isReady - new value for isReady field inside entity which will be updated
     * */
    @PatchMapping("/items/item/change-ready-state")
    ResponseEntity<Boolean> changeItemReadyState(@RequestParam Long id, @RequestParam Boolean isReady){
        return ResponseEntity.ok(itemService.changeItemReadyStateById(id, isReady));
    }
    @GetMapping("/items/by-order-id/{id}")
    ResponseEntity<List<ItemDTO>> getItemsByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemsByOrderId(id));
    }
}
