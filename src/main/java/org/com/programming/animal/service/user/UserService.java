package org.com.programming.animal.service.user;


import jakarta.validation.Valid;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.UserRepository;
import org.com.programming.animal.service.user.config.UserDetailsConfig;
import org.com.programming.animal.service.user.exception.NullPointExceptionCustomized;
import org.com.programming.animal.service.user.exception.UsuarioNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailsConfig userDetailsConfig;

    public UserService(UserRepository userRepository, UserDetailsConfig userDetailsConfig){
        this.userRepository = userRepository;
        this.userDetailsConfig = userDetailsConfig;
    }

    /* READ ALL USER TEMPORARY */
    public List<UserEntity> userEntities(){
        return userRepository.findAll();
    }

    /* CREATE USER */
    public UserEntity create(@Valid UserEntity objUser){
        objUser.setPasswordUser(userDetailsConfig.passwordEncoder().encode(objUser.getPassword()));
        return userRepository.save(objUser);
    }

//    TESTE EXCEÇÕES
    public String retornarNomeUsuario(String emailUsuario){
        UserEntity emailEncontrado = userRepository.findByEmailUser(emailUsuario);

        if (emailEncontrado == null){
            throw new NullPointExceptionCustomized();
        }
        return emailEncontrado.getNameUser();
    }

}
