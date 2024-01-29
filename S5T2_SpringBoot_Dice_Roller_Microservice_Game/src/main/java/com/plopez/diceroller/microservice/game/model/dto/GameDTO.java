package com.plopez.diceroller.microservice.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDTO {

    private int id;
    private int playerId;
    private int die1;
    private int die2;
    private int result;

}
