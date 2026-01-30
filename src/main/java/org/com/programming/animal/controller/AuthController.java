package org.com.programming.animal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.com.programming.animal.entity.DTOs.AuthRequest;
import org.com.programming.animal.entity.DTOs.AuthResponse;
import org.com.programming.animal.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;

    }

    @Operation(description = "Realiza o login do usuário.")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Realiza o login do usuário passando o e-mail e senha."),
        @ApiResponse(responseCode = "500", description = "E-mail inexistente no banco de dados. É necessário realizar o cadastro.")}
    )
    @PostMapping("/login")
    public AuthResponse loginUser(@Valid @RequestBody AuthRequest request){
        String token = userService.loginUser(request);
        return new AuthResponse(token, Instant.now());
    }

}
