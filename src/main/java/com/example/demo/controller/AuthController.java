package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserAccount;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
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

    public AuthController(
            UserAccountService userAccountService,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder
    ) {
        this.userAccountService = userAccountService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<ApiResponse> register(
            @RequestBody RegisterRequest request
    ) {
        UserAccount user = new UserAccount();
        user.setFullName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setDepartment(request.getDepartment());

        UserAccount created = userAccountService.register(user);

        return ResponseEntity.ok(
                new ApiResponse(true, "User registered successfully", created)
        );
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<ApiResponse> login(
            @RequestBody LoginRequest request
    ) {
        UserAccount user =
                userAccountService.findByEmail(request.getEmail());

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(false, "Invalid credentials", null)
            );
        }

        String token = jwtUtil.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);

        return ResponseEntity.ok(
                new ApiResponse(true, "Login successful", data)
        );
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserAccount>> getAllUsers() {
        return ResponseEntity.ok(
                userAccountService.getAllUsers()
        );
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserAccount> getUser(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                userAccountService.getUser(id)
        );
    }
}
