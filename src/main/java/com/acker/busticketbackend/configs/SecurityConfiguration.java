package com.acker.busticketbackend.configs;

import com.acker.busticketbackend.auth.user.Role;
import com.acker.busticketbackend.configs.filters.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] WHITE_LIST = {
            "/api/v1/auth/**", // For authentication
            "/api/v1/routes/**",
            "/api/v1/buses/**",
            "/api/v1/stations/**",
            "/demo", // Testing endpoint
            "/error", // This one right here is freaking important, DO NOT forget this at all.
            "/admin/**", // TODO: Needed to be removed after frontend
            "/actuator/**",
    };


    // Security Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configure HttpSecurity
        http.csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers(WHITE_LIST).permitAll() // Allows the url without any authentication
                                .requestMatchers("/api/v1/bookings/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                .anyRequest().authenticated() // Authenticate the rest
                )
                .sessionManagement(
                        session -> session // For JWT
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults());
        // Build the Security Chain
        return http.build();
    }


    // Enable CORS
    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration corsConfiguration =
                new CorsConfiguration();


        // Allow Origins
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173")); // This Allows All, Should be set to frontend url.

        // Methods
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "OPTION", "PATCH", "DELETE", "PUT")); // These two are enough for now.

        // Allow Headers
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin", "Access-Control-Request-Method",

                "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(List.of("Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));

        // This the CorsConfiguration Source.
        UrlBasedCorsConfigurationSource corsConfigurationSource =
                new UrlBasedCorsConfigurationSource();

        // We register the above CORS configuration for all path `/**`
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return corsConfigurationSource;
    }


}
