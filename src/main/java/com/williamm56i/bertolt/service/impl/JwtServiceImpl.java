package com.williamm56i.bertolt.service.impl;

import com.williamm56i.bertolt.security.BertoltAuthenticationToken;
import com.williamm56i.bertolt.security.BertoltUserDetails;
import com.williamm56i.bertolt.service.JwtService;
import com.williamm56i.bertolt.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Override
    public String generateToken(String username) {
        // TODO check valid user
        String jwt = JwtUtils.generate(username, null, 5 * 60 * 1000);
        BertoltUserDetails userDetails = new BertoltUserDetails();
        userDetails.setUsername(username);
        SecurityContextHolder.getContext().setAuthentication(new BertoltAuthenticationToken(userDetails, jwt, null));
        return jwt;
    }
}
