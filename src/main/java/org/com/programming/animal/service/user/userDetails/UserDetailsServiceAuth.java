package org.com.programming.animal.service.user.userDetails;

import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.UserJpa;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceAuth implements UserDetailsService {

    private final UserJpa userJpa;

    public UserDetailsServiceAuth(UserJpa userJpa) {
        this.userJpa = userJpa;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userJpa.findByEmailUser(email);

        if (userEntity == null){
            throw new UsernameNotFoundException("E-mail não localizado.");
        }
        return userEntity;
    }
}
