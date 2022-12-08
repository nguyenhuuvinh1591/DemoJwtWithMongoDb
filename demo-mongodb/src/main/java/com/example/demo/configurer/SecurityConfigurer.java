package com.example.demo.configurer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.example.demo.adapter.SecurityConfigurerAdapter;

import lombok.Setter;

@Setter
public class SecurityConfigurer implements SecurityConfigurerAdapter {
    public static final String URL_LOGIN = "/login";

    public static final String URL_LOGOUT = "/j_spring_security_logout";

    public static final String JSESSIONID = "JSESSIONID";
    protected AccessDeniedHandler accessDeniedHandler;
    protected AuthenticationEntryPoint authenticationEntryPoint;
    protected LogoutSuccessHandler logoutSuccessHandler;

    public SecurityConfigurer(AuthenticationEntryPoint authenticationEntryPoint,
            AccessDeniedHandler accessDeniedHandler, LogoutSuccessHandler logoutSuccessHandler) {
        super();
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.antMatcher("/**").formLogin().loginPage(URL_LOGIN).and().logout().logoutUrl(URL_LOGOUT)
                .deleteCookies(JSESSIONID).logoutSuccessHandler(this.logoutSuccessHandler).and()
                // Set unauthorized requests exception handler
                .exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)
                .accessDeniedHandler(this.accessDeniedHandler).and();
        // @formatter:on
    }
}