package com.dozie.authsystem.controller;

import com.dozie.authsystem.dto.request.ForgotPasswordRequest;
import com.dozie.authsystem.dto.request.LoginRequest;
import com.dozie.authsystem.dto.request.RegisterRequest;
import com.dozie.authsystem.dto.request.ResetPasswordRequest;
import com.dozie.authsystem.dto.request.VerifyOtpRequest;
import com.dozie.authsystem.dto.response.AuthResponse;
import com.dozie.authsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(authService.register(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, e.getMessage()));
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<AuthResponse> verifyEmail(@RequestBody VerifyOtpRequest request) {
        try {
            return ResponseEntity.ok(authService.verifyEmail(request.getEmail(), request.getOtp()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "Login Failed: " + e.getMessage()));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<AuthResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            return ResponseEntity.ok(authService.forgotPassword(request.getEmail()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, e.getMessage()));
        }
    }

    @PostMapping("/confirm-otp")
    public ResponseEntity<AuthResponse> confirmOtp(@RequestBody VerifyOtpRequest request) {
        try {
            return ResponseEntity.ok(authService.confirmOtp(request.getEmail(), request.getOtp()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, e.getMessage()));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<AuthResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            return ResponseEntity.ok(authService.resetPassword(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, e.getMessage()));
        }
    }
}
