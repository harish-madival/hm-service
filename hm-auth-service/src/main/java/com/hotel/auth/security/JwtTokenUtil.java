package com.hotel.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
import java.time.Instant;

@Component
public class JwtTokenUtil {

    private final SecretKey signingKey;
    
    private final long expirationMillis;

    public JwtTokenUtil(@Value("${jwt.secret}") String secret,
                        @Value("${jwt.expiration}") long expirationMillis) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters for HS512");
        }
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMillis = expirationMillis;
    }

    public String generateRefreshToken(String mobileNumber) {
        return generateToken(mobileNumber);
    }

    public String generateToken(String mobileNumber) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(mobileNumber)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expirationMillis)))
                .signWith(signingKey)
                .compact();
    }

    public String getMobileNumberFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false; // could log reason in debug
        }
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }
}
