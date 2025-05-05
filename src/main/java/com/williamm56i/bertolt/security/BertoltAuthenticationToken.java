package com.williamm56i.bertolt.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class BertoltAuthenticationToken extends AbstractAuthenticationToken {

    private Object principal;
    private Object credentials;

    public BertoltAuthenticationToken(Object credentials) {
        super(Collections.emptyList());
        this.credentials = credentials;
        super.setAuthenticated(false);
    }

    public BertoltAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
