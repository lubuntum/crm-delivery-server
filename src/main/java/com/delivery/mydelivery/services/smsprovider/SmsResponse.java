package com.delivery.mydelivery.services.smsprovider;

public record SmsResponse(boolean success, String messageId, String error) {
}
