package com.williamm56i.bertolt.security;

import com.williamm56i.bertolt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class BertoltUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String jwt) throws UsernameNotFoundException {
        return decodeJwt(jwt);
    }

    private UserDetails decodeJwt(String jwt) {
        Claims claims = JwtUtils.parseJwt(jwt);
        BertoltUserDetails userDetails = new BertoltUserDetails();
        userDetails.setUsername(claims.getSubject());
        userDetails.setAuthorities(null);
        if (log.isDebugEnabled()) {
            log.debug("create userDetails: {}", userDetails);
        }
        return userDetails;
    }
}
