package com.acker.busticketbackend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acker.busticketbackend.exceptions.UserNotFoundException;

import jakarta.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity register(
            @RequestBody RegisterRequest request
    ) {
        ResponseEntity response = null;
        try {
            response = ResponseEntity.ok(service.register(request));
        } catch (Exception exception) {
            response = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
        }
        return response;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateRequest(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendOtp(@RequestBody ForgotPasswordRequest request) {
        try {
            service.sendOtp(request.getEmail());
            return ResponseEntity.ok("OTP sent to email");
        } catch (UserNotFoundException | MessagingException exception) {
            return ResponseEntity.status(404).body(exception.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            service.resetPassword(request.getEmail(), request.getOtp(), request.getNewPassword());
            return ResponseEntity.ok("Password reset successfully");
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(404).body(exception.getMessage());
        } catch (RuntimeException exception) {
            return ResponseEntity.status(400).body(exception.getMessage());
        }
    }


}
