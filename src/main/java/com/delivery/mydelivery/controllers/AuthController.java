package com.delivery.mydelivery.controllers;

import com.delivery.mydelivery.database.entities.accountmanage.role.Role;
import com.delivery.mydelivery.database.services.RoleService;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    JwtService jwtService;
    @Autowired
    RoleService roleService;
    @PostMapping("/test")
    public ResponseEntity<List<Role>> testJwt(@RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(roleService.getAllRoles());

    }
    @PostMapping("/login")
    public ResponseEntity<String> login(){
        return ResponseEntity.ok(jwtService.generateToken("15"));
    }
}
