package com.example.demo.configurer;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class I18nConfiguration {
    // ENCODING
    public static final String UTF_8 = "UTF-8";

    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageResource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasenames("i18n/error_messages");
        rs.setDefaultEncoding(UTF_8);
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }
}
