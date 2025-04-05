package com.delivery.mydelivery.config.controllers;

import com.delivery.mydelivery.config.interceptors.JwtValidationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthControllerConfig implements WebMvcConfigurer {
    @Autowired
    JwtValidationInterceptor jwtValidationInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtValidationInterceptor).addPathPatterns("/api/auth/account-data");
        registry.addInterceptor(jwtValidationInterceptor).addPathPatterns("/api/auth/update-password");
    }
}
