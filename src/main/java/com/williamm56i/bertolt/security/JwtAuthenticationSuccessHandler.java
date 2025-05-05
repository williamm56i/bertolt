package com.williamm56i.bertolt.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.ByteBuffer;

@Component
@Slf4j
public class JwtAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(Boolean.TRUE.toString().getBytes("UTF-8"));
            DataBuffer dataBuffer = new DefaultDataBufferFactory().wrap(byteBuffer);
            return exchange.getResponse().writeWith(Flux.just(dataBuffer));
        } catch (IOException e) {
            log.error(e.getMessage());
            return Mono.error(e);
        }
    }
}
