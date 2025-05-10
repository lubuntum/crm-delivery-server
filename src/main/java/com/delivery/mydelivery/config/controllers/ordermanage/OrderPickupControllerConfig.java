package com.delivery.mydelivery.config.controllers.ordermanage;

import com.delivery.mydelivery.config.interceptors.authentification.JwtValidationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OrderPickupControllerConfig implements WebMvcConfigurer {
    @Autowired
    private JwtValidationInterceptor jwtValidationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtValidationInterceptor).addPathPatterns("/api/orders-pickup/**");
    }
}
