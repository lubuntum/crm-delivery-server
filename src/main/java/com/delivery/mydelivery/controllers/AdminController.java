package com.delivery.mydelivery.controllers;

import com.delivery.mydelivery.annotation.accountmanage.HasRole;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.entities.news.News;
import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.news.NewsService;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.database.services.organization.OrganizationService;
import com.delivery.mydelivery.dto.ordermanage.clientorder.ClientOrderDTO;
import com.delivery.mydelivery.dto.organization.OrganizationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    OrganizationService organizationService;
    @Autowired
    AccountService accountService;
    @Autowired
    NewsService newsService;
    @Autowired
    OrderService orderService;
    @HasRole(RoleEnum.ADMIN)
    @PostMapping("/organization/create")
    public ResponseEntity<Boolean> createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        if (!accountService.isEmailValid(organizationDTO.getDirectorData().getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
        organizationService.createOrganization(organizationDTO);
        accountService.createAccount(organizationDTO.getDirectorData());
        return ResponseEntity.ok(true);
    }
    @HasRole(RoleEnum.ADMIN)
    @PostMapping("/organization/change-status")
    public ResponseEntity<Void> changeOrganizationActiveStatus(@RequestBody OrganizationDTO organizationDTO) {
        organizationService.changeOrganizationActiveStatus(organizationDTO);
        return ResponseEntity.ok().build();
    }
    @HasRole(RoleEnum.ADMIN)
    @GetMapping("/get-organizations")
    public ResponseEntity<List<OrganizationDTO>> getOrganizations() {
        return ResponseEntity.ok(organizationService.getAllOrganizations());
    }
    @HasRole(RoleEnum.ADMIN)
    @PostMapping("/create-news")
    public ResponseEntity<Void> createNews(@RequestParam("news")String newsJson,
                                              @RequestParam(value = "imageBanner", required = false) MultipartFile imageBanner) throws IOException {
        newsService.createNews(new ObjectMapper().readValue(newsJson, News.class), imageBanner);
        return ResponseEntity.ok().build();
    }
    //Test it
    @HasRole(RoleEnum.ADMIN)
    @GetMapping("/organization/orders-between-dates")
    ResponseEntity<List<ClientOrderDTO>> getOrdersBetweenDatesByOrganization(@RequestHeader("Authorization") String token,
                                                                             @RequestParam(required = true) LocalDateTime startDate,
                                                                             @RequestParam(required = true) LocalDateTime endDate,
                                                                             @RequestParam Long organizationId) {
        return ResponseEntity.ok(orderService.getOrdersBetweenDatesForOrganization(startDate, endDate, organizationId));
    }
}
