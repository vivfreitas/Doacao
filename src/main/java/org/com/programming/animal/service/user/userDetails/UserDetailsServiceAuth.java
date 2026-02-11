package org.com.programming.animal.service.user.userDetails;

import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceAuth implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceAuth(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmailUser(email);
        if (userEntity == null){
            throw new UsernameNotFoundException("E-mail não localizado.");
        }
        return userEntity;
    }
}
