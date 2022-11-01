package com.practice.parkapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	/**
	 * ADDED ALL THE ENUM CONVERTERS FOR SPRING
	 */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSLOTNUMBERSEnumConverter());
        registry.addConverter(new StringToSLOTSTATUSEnumConverter());
        registry.addConverter(new StringToSLOTTYPEEnumConverter());
        registry.addConverter(new StringToUSERTYPEEnumConverter());
        
        
    }
}