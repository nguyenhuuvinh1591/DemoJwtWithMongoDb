package com.example.demo.adapter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface SecurityConfigurerAdapter {

    void configure(HttpSecurity http) throws Exception;
}
