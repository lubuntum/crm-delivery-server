package com.delivery.mydelivery.services.smsprovider.smsaero.dto;

public record SmsAeroMessage(
        Integer id,
        String from,
        String number,
        String text,
        String status,
        String extendStatus,
        String channel
) {
}
