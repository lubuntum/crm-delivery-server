package com.delivery.mydelivery.controllers;

import com.delivery.mydelivery.database.entities.accountmanage.role.Role;
import com.delivery.mydelivery.database.entities.registrationrequest.RegistrationRequest;
import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.accountmanage.RoleService;
import com.delivery.mydelivery.database.services.registrationrequest.RegistrationRequestService;
import com.delivery.mydelivery.dto.auth.AccountData;
import com.delivery.mydelivery.dto.auth.AuthCredential;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    RoleService roleService;
    @Autowired
    AccountService accountService;
    @Autowired
    JwtService jwtService;
    @Autowired
    RegistrationRequestService registrationRequestService;
    @PostMapping("/test")
    public ResponseEntity<List<Role>> testJwt(@RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(roleService.getAllRoles());

    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthCredential authCredential){
        try {
            return ResponseEntity.ok(accountService.validateAuthCredential(authCredential));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/update-password")
    public ResponseEntity<Boolean> updatePassword(@RequestHeader("Authorization") String token,
                                                  @RequestBody AuthCredential authCredential) {
        return ResponseEntity.ok(accountService.updatePassword(Long.valueOf(jwtService.extractSubject(token)), authCredential.getPassword()));
    }
    @GetMapping("/account-data")
    public ResponseEntity<AccountData> getAccountData(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(accountService.getAccountData(Long.valueOf(jwtService.extractSubject(token))));
    }
    @PostMapping("/registration-request")
    public ResponseEntity<Boolean> createRegistrationRequest(@RequestBody RegistrationRequest registrationRequest) {
        registrationRequestService.createRegistrationRequest(registrationRequest);
        return ResponseEntity.ok(true);
    }
}
