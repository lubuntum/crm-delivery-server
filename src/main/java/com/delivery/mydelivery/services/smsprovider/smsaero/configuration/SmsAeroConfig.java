package com.delivery.mydelivery.services.smsprovider.smsaero.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "sms.aero")
public class SmsAeroConfig {
    private String email;
    private String apiKey;
    private String url = "https://gate.smsaero.ru/v2";
    private String sign = "SMS Aero";

}
