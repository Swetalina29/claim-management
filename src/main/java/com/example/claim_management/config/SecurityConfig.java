package com.example.claim_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/claims").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/claims/{id}").permitAll()
                .requestMatchers("/api/claims/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
            .httpBasic(basic -> {});
        return http.build();
    }
}