package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
    public JwtUtil(long expirationMillis) {
        this.expirationMillis = expirationMillis;
        initKey();
    }

    // Required for Spring
    public JwtUtil() {
        this(86400000L);
    }

    // REQUIRED by portal tests
    public void initKey() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(Long userId, String email, String role) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
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
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }

    // REQUIRED by portal tests
    public Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }

    public Claims validateToken(String token) {
        return parseToken(token).getPayload();
    }

    // 🔁 REQUIRED by JwtAuthenticationFilter
    public String extractEmail(String token) {
        return validateToken(token).get("email", String.class);
    }

    // 🔁 REQUIRED by JwtAuthenticationFilter
    public String extractRole(String token) {
        return validateToken(token).get("role", String.class);
    }

    public boolean isTokenValid(String token, String username) {
        try {
            return validateToken(token).getSubject().equals(username)
                    && validateToken(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
