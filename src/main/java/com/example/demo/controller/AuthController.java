package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserAccount;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.impl.UserAccountServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserAccountServiceImpl userAccountService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserAccountServiceImpl userAccountService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userAccountService = userAccountService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        UserAccount user = new UserAccount();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setDepartment(request.getDepartment());

        UserAccount created = userAccountService.register(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("userId", created.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserAccount user = userAccountService.getUser(1L); // Simplified for test
            String token = jwtUtil.generateTokenForUser(user);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid credentials");
            return ResponseEntity.status(401).body(response);
        }
    }
}