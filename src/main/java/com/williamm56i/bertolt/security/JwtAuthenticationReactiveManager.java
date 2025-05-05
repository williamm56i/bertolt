package com.williamm56i.bertolt.security;

import com.williamm56i.bertolt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

@Slf4j
public class JwtAuthenticationReactiveManager implements ReactiveAuthenticationManager {

    @Autowired
    BertoltUserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String jwt = (String) authentication.getCredentials();
        verifyJwt(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwt);
        return Mono.just(new BertoltAuthenticationToken(userDetails, jwt, userDetails.getAuthorities()));
    }

    private String verifyJwt(String jwt) {
        if (StringUtils.isBlank(jwt)) {
            log.debug("jwt is empty or null");
            throw new BadCredentialsException("Request not have jwt");
        }
        Claims claims = JwtUtils.parseJwt(jwt);
        if (claims == null || claims.getSubject().isBlank()) {
            throw new BadCredentialsException("JWT is invalid");
        }
        return claims.getSubject();
    }
}
