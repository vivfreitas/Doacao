package org.com.programming.animal.service.user;

import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.UserRepository;
import org.com.programming.animal.service.exception.EmailExistException;
import org.com.programming.animal.service.user.config.UserDetailsConfig;
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

    /* CREATE USER */
    public UserEntity create(UserEntity objUser){
        if (userRepository.existsByEmailUser(objUser.getEmailUser())){
            throw new EmailExistException();
        }
        objUser.setPasswordUser(userDetailsConfig.passwordEncoder().encode(objUser.getPassword()));
        return userRepository.save(objUser);
    }

}
