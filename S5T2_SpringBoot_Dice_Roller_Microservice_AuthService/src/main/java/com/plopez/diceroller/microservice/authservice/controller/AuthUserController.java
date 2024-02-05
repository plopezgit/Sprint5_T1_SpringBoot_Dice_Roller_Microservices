package com.plopez.diceroller.microservice.authservice.controller;

import com.plopez.diceroller.microservice.authservice.model.dto.AuthUserDTO;
import com.plopez.diceroller.microservice.authservice.model.dto.HttpRequestDTO;
import com.plopez.diceroller.microservice.authservice.model.dto.TokenDTO;
import com.plopez.diceroller.microservice.authservice.model.entity.AuthUser;
import com.plopez.diceroller.microservice.authservice.model.service.AuthUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@SuppressWarnings("unused")
public class AuthUserController {

    @Autowired
    AuthUserService authUserService;

    @Operation(summary = "It creates an auth user.")
    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(@Valid @RequestBody AuthUserDTO authUserDTO){
        AuthUser authUser = authUserService.save(authUserDTO);
        if(authUser == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(authUser);
    }

    @Operation(summary = "It logins an auth user, generating a access token for microservices.")
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody AuthUserDTO authUserDTO){
        TokenDTO tokenDto = authUserService.login(authUserDTO);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @Operation(summary = "It validates access token.")
    @PostMapping("/validate")
    public ResponseEntity<TokenDTO> validate(@RequestParam String token, @RequestBody HttpRequestDTO httpRequestDTO){
        TokenDTO tokenDto = authUserService.validate(token, httpRequestDTO);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }


}
