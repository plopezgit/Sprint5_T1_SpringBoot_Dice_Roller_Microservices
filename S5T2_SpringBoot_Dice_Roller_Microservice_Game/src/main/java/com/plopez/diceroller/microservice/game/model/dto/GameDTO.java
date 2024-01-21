package com.plopez.diceroller.microservice.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {

    private int id;
    private int playerId;
    private byte die1;
    private byte die2;
    private String result;

}
