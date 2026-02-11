package org.com.programming.animal.service.user;

import org.com.programming.animal.entity.DTOs.AuthRequest;
import org.com.programming.animal.entity.DTOs.UserDTOsave;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.infra.jwt.TokenService;
import org.com.programming.animal.jpa.UserRepository;
import org.com.programming.animal.service.exception.EmailExistException;
import org.com.programming.animal.service.user.config.UserDetailsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
//    LOG PARA A INSTÂNCIA
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserDetailsConfig userDetailsConfig;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, UserDetailsConfig userDetailsConfig, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, TokenService tokenService){
        this.userRepository = userRepository;
        this.userDetailsConfig = userDetailsConfig;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    /* CREATE USER */
    public UserEntity create(UserDTOsave objUser){
        if (userRepository.existsByEmailUser(objUser.userEmail())){
            logger.error("O usuário não pode ser criado. Usuário: {}", objUser);
            throw new EmailExistException();
        }
        UserEntity user = new UserEntity();
        user.setNameUser(objUser.userName());
        user.setEmailUser(objUser.userEmail());
        user.setPasswordUser(userDetailsConfig.passwordEncoder().encode(objUser.userPassword()));
        UserEntity usuario = userRepository.save(user);
        logger.info("Usuário foi criado com sucesso: {}", usuario.getIdUser()); // ISSO DAQUI ESTA DANDO NULL. ARRUMAR!
        return usuario;
    }

    // Login usuário - LOAD USER NAO RETORNA USER NOT FOUND EXCEPTION. ARRUMAR.
    public String loginUser(AuthRequest authResponse){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authResponse.email(), authResponse.senha()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authResponse.email());
            final String token = tokenService.generateToken(userDetails);
            return token;

    }
}
