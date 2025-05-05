package com.williamm56i.bertolt.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Slf4j
public class JwtAuthenticationFilter extends AuthenticationWebFilter {

    BertoltUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(ReactiveAuthenticationManager authenticationManager, BertoltUserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (HttpMethod.OPTIONS.matches(exchange.getRequest().getMethod().toString())) {
            return chain.filter(exchange);
        }
        if ("/api/jwt/generateToken".equals(exchange.getRequest().getPath().toString())) {
            return chain.filter(exchange);
        }
        Authentication authentication = checkJwtAuthentication(exchange);
        return onAuthenticationSuccess(authentication,
                new WebFilterExchange(exchange, chain))
                .doOnError(AuthenticationException.class,
                        (ex) -> log.error(LogMessage.format("Authentication failed: %s", ex.getMessage()).toString()));
    }

    private Authentication checkJwtAuthentication(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String bearerToken = request.getHeaders().getFirst("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7);
            try {
                return handleAuthentication(jwt);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } else {
            exchange.getSession().flatMap(WebSession::invalidate);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    private Authentication handleAuthentication(String jwt) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwt);
        ReactiveSecurityContextHolder.getContext().subscribe((e) -> {
            log.info("handleAuthentication map" + e.getAuthentication().getName());
        });
        return new BertoltAuthenticationToken(userDetails, jwt, userDetails.getAuthorities());
    }
}
