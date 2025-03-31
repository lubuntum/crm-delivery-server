package com.delivery.mydelivery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:env.properties")
public class EnvPropertiesConfig {
    public static final String SECRET_KEY = "SECRET_KEY";
    public static final String CLIENT_URL = "CLIENT_URL";
}
