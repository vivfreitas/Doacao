package org.com.programming.animal.controller;

import org.com.programming.animal.entity.DTOs.AuthRequest;
import org.com.programming.animal.entity.DTOs.AuthResponse;
import org.com.programming.animal.infra.jwt.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;


    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        final String token = tokenService.generateToken(userDetails);
        return new AuthResponse(token);
    }

}
