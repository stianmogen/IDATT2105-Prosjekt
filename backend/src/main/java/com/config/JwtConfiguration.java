package com.config;

import com.security.config.JwtConfig;
import com.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

    @Autowired
    private JwtConfig jwtConfig;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(jwtConfig);
    }
}
