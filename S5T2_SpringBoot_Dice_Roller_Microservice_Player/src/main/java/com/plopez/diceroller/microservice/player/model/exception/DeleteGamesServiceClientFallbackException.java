package com.plopez.diceroller.microservice.player.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteGamesServiceClientFallbackException extends Exception {

    private String message;
}
