package com.delivery.mydelivery.controllers.ordermanage;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.ordermanage.status.Status;
import com.delivery.mydelivery.database.entities.ordermanage.status.StatusEnum;
import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.accountmanage.ClientService;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.database.services.organization.OrganizationService;
import com.delivery.mydelivery.database.services.productmanage.ItemService;
import com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO;
import com.delivery.mydelivery.dto.ordermanage.OrderStatusDTO;
import com.delivery.mydelivery.dto.productmanage.ItemDTO;
import com.delivery.mydelivery.dto.productmanage.ItemMapper;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/create-order")
    ResponseEntity<Long> createOrder(@RequestHeader("Authorization") String token,
                                     @RequestBody ClientOrderDTO clientOrderDTO){
        clientOrderDTO.setClientId(clientService.createClientIfNotExists(clientOrderDTO.extractClientData()));
        clientOrderDTO.setOrganizationId(accountService.getOrganizationByAccountId(Long.valueOf(jwtService.extractSubject(token))).getId());
        return ResponseEntity.ok(orderService.createOrder(clientOrderDTO));
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
    @PostMapping("/items/create-item")
    ResponseEntity<ItemDTO> createItemForOrder(@RequestBody ItemDTO itemDTO){
        return ResponseEntity.ok(ItemMapper.toDTO(itemService.createItem(itemDTO)));
    }
    @GetMapping("/items/by-order-id/{id}")
    ResponseEntity<List<ItemDTO>> getItemsByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemsByOrderId(id));
    }
}
