package com.delivery.mydelivery.config;

import com.delivery.mydelivery.config.interceptors.authentification.JwtValidationInterceptor;
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
    @Value("${images.folder}")
    private String imagesFolder;
    @Value("${banners.folder}")
    private String bannersFolder;
    //where agreements docs is stored
    @Value("${documents.agreements}")
    private String documentsAgreements;
    @Value("${web.documents.agreements}")
    private String webDocumentsAgreements;
    public WebConfig(Environment env ) {
        this.env = env;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedOrigins(env.getProperty(EnvPropertiesConfig.CLIENT_URL))
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imagesFolder + "/");
        registry.addResourceHandler("/banners/**")
                .addResourceLocations("file:" + bannersFolder + "/");
        registry.addResourceHandler("/" + webDocumentsAgreements + "**")
                .addResourceLocations("file:" + documentsAgreements + "/");
    }
}
