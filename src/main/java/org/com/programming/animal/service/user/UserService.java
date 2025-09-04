package org.com.programming.animal.service.user;


import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.UserJpa;
import org.com.programming.animal.service.user.config.UserDetailsConfig;
import org.com.programming.animal.service.user.userDetails.UserDetailsServiceAuth;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
public class UserService {

    private final UserJpa userJpa;
    private final UserDetailsConfig userDetailsConfig;

    public UserService(UserJpa userJpa, UserDetailsConfig userDetailsConfig){
        this.userJpa = userJpa;
        this.userDetailsConfig = userDetailsConfig;
    }

    /* READ ALL USER TEMPORARY */
    public List<UserEntity> userEntities(){
        return userJpa.findAll();
    }

    /* CREATE USER */
    public UserEntity create(UserEntity objUser){
        objUser.setPasswordUser(userDetailsConfig.passwordEncoder().encode(objUser.getPassword()));
        return userJpa.save(objUser);
    }


}
