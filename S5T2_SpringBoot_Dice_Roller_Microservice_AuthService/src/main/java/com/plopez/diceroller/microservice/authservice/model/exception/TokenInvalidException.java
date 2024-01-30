package com.plopez.diceroller.microservice.authservice.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInvalidException extends RuntimeException{
    private String message;
}
