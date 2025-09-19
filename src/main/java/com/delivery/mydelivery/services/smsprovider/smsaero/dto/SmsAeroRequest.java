package com.delivery.mydelivery.services.smsprovider.smsaero.dto;

public record SmsAeroRequest(
        String number,
        String text,
        String sign,
        String channel
) {
}
