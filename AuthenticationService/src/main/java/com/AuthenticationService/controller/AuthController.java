package com.AuthenticationService.controller;


import com.AuthenticationService.dto.LoginRequest;
import com.AuthenticationService.dto.LoginResponse;
import com.AuthenticationService.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        if ("akshat".equals(request.getUsername())
                && "1234".equals(request.getPassword())) {

            String token = jwtService.generateToken(request.getUsername());

            return new LoginResponse(token);
        }

        throw new RuntimeException("Invalid username or password");
    }
}