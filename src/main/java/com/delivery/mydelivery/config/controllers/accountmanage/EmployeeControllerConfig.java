package com.delivery.mydelivery.config.controllers.accountmanage;

import com.delivery.mydelivery.config.interceptors.authentification.JwtValidationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EmployeeControllerConfig implements WebMvcConfigurer {
    @Autowired
    JwtValidationInterceptor jwtValidationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtValidationInterceptor).addPathPatterns("/api/employees/**");
    }
}
