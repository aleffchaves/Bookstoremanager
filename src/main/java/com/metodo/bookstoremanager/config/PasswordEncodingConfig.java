package com.metodo.bookstoremanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncodingConfig {

    @Bean
    public PasswordEncoder passwordEncoding() {
        return new BCryptPasswordEncoder();
    }

}
