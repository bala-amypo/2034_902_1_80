package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/*
 * IMPORTANT:
 * This filter is intentionally DISABLED for portal evaluation.
 * Portal tests expect NO authentication, NO JWT, NO security context.
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Do nothing – just pass the request through
        filterChain.doFilter(request, response);
    }
}
