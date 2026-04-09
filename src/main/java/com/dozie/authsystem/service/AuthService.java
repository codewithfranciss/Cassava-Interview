package com.dozie.authsystem.service;

import com.dozie.authsystem.dto.request.LoginRequest;
import com.dozie.authsystem.dto.request.RegisterRequest;
import com.dozie.authsystem.dto.request.ResetPasswordRequest;
import com.dozie.authsystem.dto.response.AuthResponse;
import com.dozie.authsystem.model.User;
import com.dozie.authsystem.repository.UserRepository;
import com.dozie.authsystem.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            JwtService jwtService, AuthenticationManager authenticationManager,
            EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String otp = generateOtp();

        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFullName(),
                false,
                otp,
                LocalDateTime.now().plusMinutes(10));

        userRepository.save(user);

        emailService.sendOtpEmail(user.getEmail(), otp);

        return new AuthResponse(null, "User registered successfully. Please check your email for the OTP.");
    }

    public AuthResponse verifyEmail(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isVerified()) {
            return new AuthResponse(null, "User is already verified");
        }

        if (user.getOtp() == null || !user.getOtp().equals(otp) || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        user.setVerified(true);
        user.setOtp(null);
        user.setOtpExpiry(null);
        userRepository.save(user);

        return new AuthResponse(null, "Email verified successfully. You can now login.");
    }

    public AuthResponse login(LoginRequest request) {
        // This will automatically process the password and throw exception if wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isVerified()) {
            throw new RuntimeException("Please verify your email before logging in.");
        }

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken, "Login successful");
    }

    public AuthResponse forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        emailService.sendOtpEmail(user.getEmail(), otp);

        return new AuthResponse(null, "OTP sent to your email for password reset.");
    }

    public AuthResponse confirmOtp(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getOtp() == null || !user.getOtp().equals(otp) || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        return new AuthResponse(null, "OTP confirmed. You may now reset your password.");
    }

    public AuthResponse resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getOtp() == null || !user.getOtp().equals(request.getOtp())
                || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setOtp(null);
        user.setOtpExpiry(null);
        userRepository.save(user);

        return new AuthResponse(null, "Password reset successfully. You can now login with your new password.");
    }

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
