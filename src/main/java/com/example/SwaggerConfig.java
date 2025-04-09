package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Swagger UI
 * 
 * Note: Most of the configuration is done in application.properties
 */
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * Add resource handlers to expose the openapi.yaml file
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/openapi.yaml")
                .addResourceLocations("classpath:/");
    }
}
