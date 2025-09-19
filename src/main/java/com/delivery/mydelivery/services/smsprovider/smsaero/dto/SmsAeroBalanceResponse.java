package com.delivery.mydelivery.services.smsprovider.smsaero.dto;

import java.math.BigDecimal;

public record SmsAeroBalanceResponse(
        boolean success,
        String message,
        BigDecimal data
) {}
