package com.plopez.diceroller.microservice.authservice.controller;

import com.plopez.diceroller.microservice.authservice.model.dto.AuthUserDTO;
import com.plopez.diceroller.microservice.authservice.model.dto.HttpRequestDTO;
import com.plopez.diceroller.microservice.authservice.model.dto.TokenDTO;
import com.plopez.diceroller.microservice.authservice.model.entity.AuthUser;
import com.plopez.diceroller.microservice.authservice.model.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@SuppressWarnings("unused")
public class AuthUserController {

    @Autowired
    AuthUserService authUserService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthUserDTO authUserDTO){
        TokenDTO tokenDto = authUserService.login(authUserDTO);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDTO> validate(@RequestParam String token, @RequestBody HttpRequestDTO httpRequestDTO){
        TokenDTO tokenDto = authUserService.validate(token, httpRequestDTO);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(@RequestBody AuthUserDTO authUserDTO){
        AuthUser authUser = authUserService.save(authUserDTO);
        if(authUser == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(authUser);
    }
}
