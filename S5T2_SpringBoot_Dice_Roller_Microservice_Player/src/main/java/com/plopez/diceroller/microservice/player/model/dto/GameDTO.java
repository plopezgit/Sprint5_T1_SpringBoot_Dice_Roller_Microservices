package com.plopez.diceroller.microservice.player.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {

    private int die1;
    private int die2;
    private int result;
    private int playerId;

}
