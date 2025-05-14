package com.example.CustomerTracking.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/customers/**").permitAll()
                        .requestMatchers("/orders/**").permitAll()
                        .requestMatchers("/products/**").permitAll()
                        .requestMatchers("/brands/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}