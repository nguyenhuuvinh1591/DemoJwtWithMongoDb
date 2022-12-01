package com.example.demo.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.exception.ErrorHandler;
import com.example.demo.exception.SuccessHandler;
import com.example.demo.exception.imlp.ErrorHandlerImpl;
import com.example.demo.exception.imlp.SuccessHandlerImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class CommonBeanConfigurer {

    @Bean
    public ErrorHandler getErrorHandler() {
        return new ErrorHandlerImpl();
    }

    @Bean
    public SuccessHandler getSuccessHandler() {
        return new SuccessHandlerImpl();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule());
    }

}
