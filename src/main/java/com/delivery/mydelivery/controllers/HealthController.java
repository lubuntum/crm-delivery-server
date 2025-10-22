package com.delivery.mydelivery.controllers;

import com.delivery.mydelivery.dto.auth.HealthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    @GetMapping()
    public HealthResponse checkHealthStatus() {
        return new HealthResponse("OK", LocalDateTime.now());
    }
}
