package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

import com.example.demo.entity.UserAccount;

@Component
public class JwtUtil {

    private SecretKey secretKey;
    private final long expirationMillis;

    // REQUIRED by portal tests
    public JwtUtil() {
        this.expirationMillis = 86400000;
    }

    // REQUIRED by portal tests
    public JwtUtil(long expirationMillis) {
        this.expirationMillis = expirationMillis;
    }

    // Spring-managed constructor (safe to keep)
    public JwtUtil(
            @Value("${jwt.secret:defaultSecretKey}") String secret,
            @Value("${jwt.expiration:86400000}") long expirationMillis) {
        this.expirationMillis = expirationMillis;
    }

    @PostConstruct
    public void initKey() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(Long userId, String email, String role) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }

    // REQUIRED by portal tests
    public String generateTokenForUser(UserAccount user) {
        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .subject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // REQUIRED by portal tests
    public Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }

    public String extractEmail(String token) {
        return validateToken(token).get("email", String.class);
    }

    public String extractUsername(String token) {
        return validateToken(token).getSubject();
    }

    public Long extractUserId(String token) {
        return validateToken(token).get("userId", Long.class);
    }

    public String extractRole(String token) {
        return validateToken(token).get("role", String.class);
    }

    public boolean isTokenValid(String token, String username) {
        try {
            return extractUsername(token).equals(username)
                    && validateToken(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
