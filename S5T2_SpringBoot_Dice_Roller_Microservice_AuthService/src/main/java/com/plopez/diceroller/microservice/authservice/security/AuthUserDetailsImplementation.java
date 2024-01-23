package com.plopez.diceroller.microservice.authservice.security;

import com.plopez.diceroller.microservice.authservice.model.entity.AuthUser;
import com.plopez.diceroller.microservice.authservice.model.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class AuthUserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private AuthUserRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<AuthUser> userInfoOptional = authRepository.findByUserName(userName);
        if (userInfoOptional.isPresent()) {
            AuthUser userInfo = userInfoOptional.get();
            return User.builder()
                    .username(userInfo.getUserName())
                    .password(userInfo.getPassword())
                    .build();
        } else {
            throw new UsernameNotFoundException("username: " + userName + " not found");
        }

    }
}
