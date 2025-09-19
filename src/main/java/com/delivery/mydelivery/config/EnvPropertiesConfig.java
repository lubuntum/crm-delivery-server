package com.delivery.mydelivery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:env.properties")
public class EnvPropertiesConfig {
    public static final String SECRET_KEY = "SECRET_KEY";
    public static final String CLIENT_URL = "CLIENT_URL";
    public static final String TG_BOT_TOKEN = "TG_BOT_TOKEN";
    public static final String SMS_AERO_TOKEN = "SMS_AERO_TOKEN";
    public static final String DEFAULT_PASSWORD = "DEFAULT_PASSWORD";
}
