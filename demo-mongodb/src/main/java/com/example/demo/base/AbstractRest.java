package com.example.demo.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.exception.ErrorHandler;
import com.example.demo.exception.SuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@ComponentScan
public abstract class AbstractRest {

    @Autowired
    protected ErrorHandler errorHandler;

    @Autowired
    protected SuccessHandler successHandler;

    @Autowired
    protected ObjectMapper objectMapper;
}
