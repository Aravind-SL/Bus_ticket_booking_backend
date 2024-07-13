package com.acker.busticketbackend.auth;

import com.acker.busticketbackend.exceptions.PasswordMismatchException;
import com.acker.busticketbackend.exceptions.UserAlreadyExistException;
import com.acker.busticketbackend.exceptions.UserNotFoundException;

import jakarta.mail.MessagingException;

import com.acker.busticketbackend.auth.user.Role;
import com.acker.busticketbackend.auth.user.User;
import com.acker.busticketbackend.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    private final OTPService otpService;

    private final EmailService emailService;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Email ID Does not exist."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new PasswordMismatchException("Incorrect password");
        }
        
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("User with email " + request.getEmail() + " already exists");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException("Password and Confirm Password do not match");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();

    }

    public void sendOtp(String email) throws UserNotFoundException, MessagingException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        String otp = otpService.generateOTP(email);
        emailService.sendOtpEmail(email, otp);
    }
    
    public void resetPassword(String email, String otp, String newPassword) throws UserNotFoundException {
        if (!otpService.validateOTP(email, otp)) {
            throw new RuntimeException("Invalid OTP");
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
