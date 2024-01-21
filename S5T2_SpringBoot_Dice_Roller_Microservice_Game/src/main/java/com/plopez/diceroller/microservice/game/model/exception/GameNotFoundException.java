package com.plopez.diceroller.microservice.game.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameNotFoundException extends Exception {

    private String message;
}
