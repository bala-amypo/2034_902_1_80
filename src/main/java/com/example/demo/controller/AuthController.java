
package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserAccount;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {
    
    private final UserAccountService userAccountService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserAccountService userAccountService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userAccountService = userAccountService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register a new user account")
    public ResponseEntity<ApiResponse<UserAccount>> register(@RequestBody RegisterRequest request) {
        UserAccount user = new UserAccount();
        user.setFullName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setDepartment(request.getDepartment());
        
        UserAccount createdUser = userAccountService.register(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", createdUser));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@RequestBody LoginRequest request) {
        UserAccount user = userAccountService.findByEmail(request.getEmail());
        
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user);
            
            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", response));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid credentials"));
        }
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Get list of all users (Admin only)")
    public ResponseEntity<ApiResponse<List<UserAccount>>> getAllUsers() {
        List<UserAccount> users = userAccountService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully", users));
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get user by ID", description = "Get user details by ID (Admin only)")
    public ResponseEntity<ApiResponse<UserAccount>> getUserById(@Parameter(name = "id", description = "User ID") @PathVariable Long id) {
        UserAccount user = userAccountService.getUser(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", user));
    }
}