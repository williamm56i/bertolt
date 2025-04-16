package com.williamm56i.bertolt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtils {

    private static Key secretKey;

    public static String generate(String username, Object roleList, long tokenExpireTime) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key secretKey = generalKey();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roleList);
        JwtBuilder builder = Jwts.builder()
                .signWith(secretKey, signatureAlgorithm)
                .setClaims(claims)
                .setSubject(username)
                .setExpiration(getExpireDate(tokenExpireTime));
        return builder.compact();
    }

    public static String refresh(String jwt, long tokenExpireTime) {
        Claims claims = parseJwt(jwt);
        if (claims != null && claims.getSubject() != null && claims.get("roles") != null) {
            return generate(claims.getSubject(), claims.get("roles"), tokenExpireTime);
        }
        return null;
    }

    public static Claims parseJwt(String jwt) {
        Key secretKey = generalKey();
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private static Key generalKey() {
        if (secretKey == null) {
            secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
        return secretKey;
    }

    private static Date getExpireDate(long tokenExpireTime) {
        long current = System.currentTimeMillis();
        long exp = current + tokenExpireTime;
        Date expireDate = new Date(exp);
        log.info("expireDate: " + expireDate);
        return expireDate;
    }
}
