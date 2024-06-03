package com.acker.busticketbackend.auth;

import com.acker.busticketbackend.models.user.Role;
import com.acker.busticketbackend.models.user.User;
import com.acker.busticketbackend.models.user.UserRepository;
import com.acker.busticketbackend.services.JWTService;
import lombok.RequiredArgsConstructor;
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


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println("user");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();


        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponse register(RegisterRequest request) throws Exception {

        if (request.getPassword().equals(request.getConfirmPassword())) {
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

        throw new Exception("Password and Confirm Password Doesn't Match");

    }

}
