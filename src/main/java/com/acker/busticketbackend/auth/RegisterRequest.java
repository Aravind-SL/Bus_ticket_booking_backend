package com.acker.busticketbackend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;

}
