package com.delivery.mydelivery.controllers.accountmanage;

import com.delivery.mydelivery.annotation.accountmanage.HasRole;
import com.delivery.mydelivery.config.EnvPropertiesConfig;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatusEnum;
import com.delivery.mydelivery.database.entities.accountmanage.role.Role;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.entities.news.News;
import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.accountmanage.EmployeeService;
import com.delivery.mydelivery.database.services.accountmanage.RoleService;
import com.delivery.mydelivery.database.services.news.NewsService;
import com.delivery.mydelivery.dto.accountmanage.AccountResetData;
import com.delivery.mydelivery.dto.auth.AccountData;
import com.delivery.mydelivery.dto.auth.AuthCredential;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RoleService roleService;
    @Autowired
    JwtService jwtService;
    @Autowired
    NewsService newsService;
    @Autowired
    Environment env;
    @HasRole(RoleEnum.DIRECTOR)
    @GetMapping("/by-organization")
    public ResponseEntity<List<AccountData>> getAccountsByOrganization(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                accountService.getAccountsDataByOrganizationId(
                        accountService.getOrganizationByAccountId(
                                Long.valueOf(jwtService.extractSubject(token))).getId()));
    }
    @HasRole({RoleEnum.DIRECTOR, RoleEnum.ADMIN})
    @PostMapping("/update-status")
    public ResponseEntity<ActiveStatusEnum> updateAccountStatus(@RequestBody AccountData accountData) {
        return ResponseEntity.ok(accountService.updateAccountStatus(accountData.getId(), accountData.getAccountStatus()));
    }
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAccountsRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }
    @HasRole(RoleEnum.DIRECTOR)
    @PostMapping("/create")
    public ResponseEntity<Long> createAccount(@RequestHeader("Authorization") String token,
                                                @RequestBody AccountData accountData) {
        accountData.setOrganizationId(accountService.getOrganizationByAccountId(
                Long.valueOf(jwtService.extractSubject(token))).getId());
        return ResponseEntity.ok(accountService.createAccount(accountData));
    }
    @GetMapping("/get-recent-news")
    public ResponseEntity<News> getRecentNews(){
        return ResponseEntity.ok(newsService.getRecentNews());
    }
    @HasRole(RoleEnum.DIRECTOR)
    @PostMapping("/reset-password")
    public ResponseEntity<Boolean> resetPasswordForAccount(@RequestBody AccountResetData accountResetData) {
        return ResponseEntity.ok(
                accountService.updatePassword(
                        accountResetData.getAccountId(),
                        env.getProperty(EnvPropertiesConfig.DEFAULT_PASSWORD)));
    }
    @PostMapping("/update")
    public ResponseEntity<Boolean> updateAccountData(@RequestBody AccountData accountData) {
        return ResponseEntity.ok(accountService.updateAccountData(accountData));
    }
}
