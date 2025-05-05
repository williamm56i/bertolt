package com.williamm56i.bertolt.config;

import com.williamm56i.bertolt.security.BertoltUserDetailsService;
import com.williamm56i.bertolt.security.JwtAuthenticationFilter;
import com.williamm56i.bertolt.security.JwtAuthenticationReactiveManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    @Autowired
    BertoltUserDetailsService userDetailsService;

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable);

        http.securityMatcher(ServerWebExchangeMatchers.anyExchange())
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers("/api/jwt/generateToken").permitAll()
                        .anyExchange().authenticated())
                .addFilterAfter(new JwtAuthenticationFilter(jwtAuthenticationReactiveManager(), userDetailsService), SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }

    public JwtAuthenticationReactiveManager jwtAuthenticationReactiveManager() {
        return new JwtAuthenticationReactiveManager();
    }
}
