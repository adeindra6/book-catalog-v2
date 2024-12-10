package com.adeindra6.catalog.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.adeindra6.catalog.security.util.JWTTokenFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class AppConfig {

    @Bean
    public SecretKey key() {
        byte[] keyBytes = Decoders.BASE64.decode("9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Bean
    public JWTTokenFactory jwtTokenFactory(SecretKey secret) {
        return new JWTTokenFactory(secret);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
