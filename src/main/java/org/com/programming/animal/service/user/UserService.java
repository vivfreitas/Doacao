package org.com.programming.animal.service.user;

import org.com.programming.animal.entity.DTOs.AuthRequest;
import org.com.programming.animal.entity.DTOs.UserDTOsave;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.infra.jwt.TokenService;
import org.com.programming.animal.jpa.UserRepository;
import org.com.programming.animal.globalExceptions.exception.EmailExistException;
import org.com.programming.animal.globalExceptions.exception.EmailNotFoundException;
import org.com.programming.animal.service.user.userDetails.UserDetailsServiceAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {
//    LOG PARA A INSTÂNCIA
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceAuth userDetailsServiceAuth;

    private final TokenService tokenService;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsServiceAuth userDetailsServiceAuth, TokenService tokenService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceAuth = userDetailsServiceAuth;
        this.tokenService = tokenService;
    }

    // Criando Usuário
    public UserEntity create(UserDTOsave objUser){
        if (userRepository.existsByEmailUser(objUser.userEmail())){
            logger.debug("Usuário tentou realizar cadastro com e-mail já cadastrado. E-mail utilizado {}", objUser.userEmail());
            logger.error("O usuário não pode ser criado.");
            throw new EmailExistException();
        }
        UserEntity user = new UserEntity();
        user.setNameUser(objUser.userName());
        user.setEmailUser(objUser.userEmail());
        user.setPasswordUser(passwordEncoder.encode(objUser.userPassword()));
        UserEntity usuario = userRepository.save(user);
        logger.debug("Usuário criado com sucesso!");
        logger.info("Usuário foi criado com sucesso: {}", usuario.getIdUser());
        return usuario;
    }

    // Login Usuário
    public String loginUser(AuthRequest authResponse){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authResponse.email(),
                            authResponse.senha())
            );
            UserDetails userDetails = userDetailsServiceAuth.loadUserByUsername(authResponse.email());
            String token = tokenService.generateToken(userDetails);
            logger.debug("Token criado: {}", token);
            logger.info("Token do usuário criado às {}", Instant.now());
            return token;
        }catch (BadCredentialsException e){
            logger.debug("E-mail e/ou senha incorretos. Não foi possível gerar token. {}", authResponse);
            logger.warn("Falha ao fazer login do usuário. Credenciais incorretas.");
            throw new EmailNotFoundException(authResponse.email());
        }
    }


}
