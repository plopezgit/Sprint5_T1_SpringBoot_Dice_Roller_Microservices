package com.plopez.diceroller.microservice.authservice.model.service;

import com.plopez.diceroller.microservice.authservice.model.dto.AuthUserDTO;
import com.plopez.diceroller.microservice.authservice.model.dto.HttpRequestDTO;
import com.plopez.diceroller.microservice.authservice.model.dto.TokenDTO;
import com.plopez.diceroller.microservice.authservice.model.entity.AuthUser;
import com.plopez.diceroller.microservice.authservice.model.exception.AuthUserAlreadyExistException;
import com.plopez.diceroller.microservice.authservice.model.exception.AuthUserInvalidException;
import com.plopez.diceroller.microservice.authservice.model.exception.AuthUserNotFoundException;
import com.plopez.diceroller.microservice.authservice.model.exception.TokenInvalidException;
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
            throw new AuthUserAlreadyExistException("The user already exist.");
        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .userName(dto.getUserName())
                .password(password)
                .role(dto.getRole())
                .build();
        return authUserRepository.save(authUser);
    }

    public TokenDTO login(AuthUserDTO dto) {
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(user.isEmpty())
            throw new AuthUserNotFoundException("This user is not registered");
        if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword()))
            return new TokenDTO(jsonWebTokenProvider.createToken(user.get()));
        throw new AuthUserInvalidException("The user is invalid");
    }

    public TokenDTO validate(String token, HttpRequestDTO httpRequestDTO) {
        if(!jsonWebTokenProvider.validate(token, httpRequestDTO))
            throw new TokenInvalidException("The token is invalid");
        String username = jsonWebTokenProvider.getUserNameFromToken(token);
        if(authUserRepository.findByUserName(username).isEmpty())
            throw new AuthUserNotFoundException("This user is not registered");
        return new TokenDTO(token);
    }
}
