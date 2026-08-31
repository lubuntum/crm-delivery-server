package com.delivery.mydelivery.config;

import com.delivery.mydelivery.config.interceptors.authentification.JwtValidationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private Environment env;
    @Autowired
    JwtValidationInterceptor jwtValidationInterceptor;
    @Value("${public.folder}")
    private String publicFolder;
    public WebConfig(Environment env ) {
        this.env = env;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // Allow ALL origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(false)  // Must be false when using "*" for origins
                .maxAge(3600);  // Cache preflight response for 1 hour
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ===== ONE RULE TO RULE THEM ALL =====
        // ANY file in public folder is accessible
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + publicFolder);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtValidationInterceptor)
                .addPathPatterns(
                        // Account Management
                        "/api/accounts/**",
                        "/api/employees/**",

                        // Order Management
                        "/api/orders/**",
                        "/api/orders-inspection/**",
                        "/api/orders-pickup/**",
                        "/api/sync/**",

                        // Admin & Organization
                        "/api/admin/**",
                        "/api/organization/**",

                        // SMS
                        "/api/sms/**",

                        // Auth (specific protected endpoints)
                        "/api/auth/account-data",
                        "/api/auth/update-password"
                )
                .excludePathPatterns(
                        // Public endpoints (no JWT validation)
                        "/api/auth/login",
                        "/api/auth/register",
                        "/api/auth/refresh-token"
                );
    }
}
