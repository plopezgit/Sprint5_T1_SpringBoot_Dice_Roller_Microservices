package com.plopez.diceroller.microservice.authservice.model.service;

import com.plopez.diceroller.microservice.authservice.model.dto.AuthUserDTO;
import com.plopez.diceroller.microservice.authservice.model.dto.HttpRequestDTO;
import com.plopez.diceroller.microservice.authservice.model.dto.TokenDTO;
import com.plopez.diceroller.microservice.authservice.model.entity.AuthUser;
import com.plopez.diceroller.microservice.authservice.model.repository.AuthUserRepository;
import com.plopez.diceroller.microservice.authservice.security.JsonWebTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class AuthUserService {

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JsonWebTokenProvider jsonWebTokenProvider;

    public AuthUser save(AuthUserDTO dto) {
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(user.isPresent())
            return null;
        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .userName(dto.getUserName())
                .password(password)
                .role(dto.getUserName())
                .build();
        return authUserRepository.save(authUser);
    }

    public TokenDTO login(AuthUserDTO dto) {
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(!user.isPresent())
            return null;
        if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword()))
            return new TokenDTO(jsonWebTokenProvider.createToken(user.get()));
        return null;
    }

    public TokenDTO validate(String token, HttpRequestDTO httpRequestDTO) {
        if(!jsonWebTokenProvider.validate(token, httpRequestDTO))
            return null;
        String username = jsonWebTokenProvider.getUserNameFromToken(token);
        if(!authUserRepository.findByUserName(username).isPresent())
            return null;
        return new TokenDTO(token);
    }
}
