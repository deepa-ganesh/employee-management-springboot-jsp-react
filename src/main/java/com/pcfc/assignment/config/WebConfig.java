package com.pcfc.assignment.config;

import com.pcfc.assignment.converter.EmployeeDtoToEntityConverter;
import com.pcfc.assignment.converter.EmployeeEntityToDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public EmployeeDtoToEntityConverter employeeDtoToEntityConverter() {
        return new EmployeeDtoToEntityConverter();
    }

    @Bean
    public EmployeeEntityToDtoConverter employeeEntityToDtoConverter() {
        return new EmployeeEntityToDtoConverter();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(employeeDtoToEntityConverter());
        registry.addConverter(employeeEntityToDtoConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/", "classpath:/static/", "/WEB-INF/resources/", "/resources/**", "classpath:/public/");
    }
}