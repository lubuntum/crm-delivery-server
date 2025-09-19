package com.delivery.mydelivery.services.smsprovider.smsaero.dto;

import java.util.List;

public record SmsAeroResponse(
        boolean success,
        String message,
        List<SmsAeroMessage> smsAeroMessages
) {}
