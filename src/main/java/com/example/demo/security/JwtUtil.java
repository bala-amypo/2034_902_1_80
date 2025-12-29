package com.example.demo.security;

import com.example.demo.entity.UserAccount;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret:mySecretKey}")
    private String secret;
    
    @Value("${jwt.expiration:86400000}")
    private long expiration;
    
    private SecretKey key;
    
    public void initKey() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    public String generateToken(Map<String, Object> claims, String subject) {
        if (key == null) initKey();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }
    
    public String generateTokenForUser(UserAccount user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        return generateToken(claims, user.getEmail());
    }
    
    public JwtWrapper parseToken(String token) {
        if (key == null) initKey();
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return new JwtWrapper(jws);
    }
    
    public String extractUsername(String token) {
        return parseToken(token).getPayload().getSubject();
    }
    
    public Long extractUserId(String token) {
        return Long.valueOf(parseToken(token).getPayload().get("userId").toString());
    }
    
    public String extractRole(String token) {
        return parseToken(token).getPayload().get("role").toString();
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
        return parseToken(token).getPayload().getExpiration().before(new Date());
    }
    
    public static class JwtWrapper {
        private final Jws<Claims> jws;
        
        public JwtWrapper(Jws<Claims> jws) {
            this.jws = jws;
        }
        
        public Claims getPayload() {
            return jws.getBody();
        }
    }
}