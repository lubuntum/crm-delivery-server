package com.delivery.mydelivery.services.smsprovider;

import java.util.List;

public record SmsRequest(String to, String message, String from) {
}
