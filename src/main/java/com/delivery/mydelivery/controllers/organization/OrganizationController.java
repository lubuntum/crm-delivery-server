package com.delivery.mydelivery.controllers.organization;

import com.delivery.mydelivery.annotation.accountmanage.CheckStatus;
import com.delivery.mydelivery.annotation.accountmanage.HasRole;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.entities.organization.OrganizationDetails;
import com.delivery.mydelivery.database.projections.MaterialProjection;
import com.delivery.mydelivery.database.projections.ordermanage.OrdersTotalStats;
import com.delivery.mydelivery.database.projections.organization.OrganizationDetailsProjection;
import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.database.services.organization.OrganizationDetailsService;
import com.delivery.mydelivery.database.services.productmanage.MaterialService;
import com.delivery.mydelivery.dto.ordermanage.clientorder.ClientOrderDTO;
import com.delivery.mydelivery.dto.ordermanage.clientorder.ClientOrderWithItemsDTO;
import com.delivery.mydelivery.dto.productmanage.MaterialDTO;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {
    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;

    @Autowired
    MaterialService materialService;
    @Autowired
    OrganizationDetailsService organizationDetailsService;
    @Autowired
    JwtService jwtService;

    @GetMapping("/orders")
    @CheckStatus
    public ResponseEntity<List<ClientOrderDTO>> getOrdersByOrganizationId(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(orderService.getAllOrdersByOrganizationId(
                accountService.getOrganizationByAccountId(
                        Long.valueOf(jwtService.extractSubject(token))).getId()));
    }
    @GetMapping("/orders-with-items")
    @CheckStatus
    public ResponseEntity<List<ClientOrderWithItemsDTO>> getOrdersWithItemsByOrganizationId(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(orderService.getAllOrdersWithItemsByOrganizationId(
                accountService.getOrganizationByAccountId(
                        Long.valueOf(jwtService.extractSubject(token))).getId()));
    }
    @GetMapping("/materials")
    public ResponseEntity<List<MaterialProjection>> getMaterialsByOrganization(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(materialService.getMaterialsByOrganizationId(
                accountService.getOrganizationByAccountId(Long.valueOf(jwtService.extractSubject(token))).getId()));
    }
    //TODO test it
    @PostMapping("/materials/create")
    public ResponseEntity<Long> addMaterialForOrganization(@RequestHeader("Authorization") String token,
                                                             @RequestBody MaterialDTO materialDTO) {
        //before add material find organization by provided tken
        materialDTO.setOrganizationId(
                accountService.getOrganizationByAccountId(
                        Long.valueOf(jwtService.extractSubject(token))).getId());
        return ResponseEntity.ok(materialService.createMaterial(materialDTO));
    }
    @PostMapping("/materials/remove")
    public ResponseEntity<Boolean> removeMaterialForOrganization(@RequestBody MaterialDTO materialDTO) {
        return ResponseEntity.ok(materialService.removeMaterial(materialDTO));
    }
    @GetMapping("/statistics/remain-orders")
    public ResponseEntity<OrdersTotalStats> getOrdersTotalStatsByOrganization(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(orderService
                .getRemainOrdersStatsByOrganizationId(
                        accountService.getOrganizationByAccountId(
                                Long.valueOf(jwtService.extractSubject(token))).getId()));
    }
    @GetMapping("/organization-details")
    public ResponseEntity<OrganizationDetailsProjection> getOrganizationDetails(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(organizationDetailsService.getOrganizationDetailsByOrganizationId(
                accountService.getOrganizationByAccountId(
                        Long.valueOf(jwtService.extractSubject(token))).getId()));
    }
    @HasRole(RoleEnum.DIRECTOR)
    @PostMapping("/update-organization-details")
    public ResponseEntity<Boolean> updateOrganizationDetails(@RequestHeader("Authorization") String token, @RequestBody OrganizationDetails organizationDetails) {
        organizationDetails.setOrganization(
                accountService.getOrganizationByAccountId(
                        Long.valueOf(jwtService.extractSubject(token))));
        organizationDetailsService.updateOrganizationDetails(organizationDetails);
        return ResponseEntity.ok(true);
    }

}
