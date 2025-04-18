package com.delivery.mydelivery.config;

import com.delivery.mydelivery.config.interceptors.JwtValidationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private Environment env;
    private JwtValidationInterceptor jwtValidationInterceptor;
    public WebConfig(Environment env, JwtValidationInterceptor jwtValidationInterceptor) {
        this.env = env;
        this.jwtValidationInterceptor = jwtValidationInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedOrigins(env.getProperty(EnvPropertiesConfig.CLIENT_URL))
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtValidationInterceptor).addPathPatterns("/api/auth/test");
    }
}
