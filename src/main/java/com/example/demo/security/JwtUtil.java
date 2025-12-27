package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
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
    
    public JwtUtil(@Value("${jwt.secret:defaultSecretKey}") String secret, 
                   @Value("${jwt.expiration:86400000}") long expirationMillis) {
        this.expirationMillis = expirationMillis;
        initKey();
    }
    
    public JwtUtil() {
        this.expirationMillis = 86400000;
        initKey();
    }
    
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
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }
    
    public String generateTokenForUser(UserAccount user) {
        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }
    
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
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
            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isTokenExpired(String token) {
        return validateToken(token).getExpiration().before(new Date());
    }
}