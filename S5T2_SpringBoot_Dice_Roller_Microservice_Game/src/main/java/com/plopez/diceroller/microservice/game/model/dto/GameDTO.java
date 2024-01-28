package com.plopez.diceroller.microservice.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

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

    public GameDTO(int playerId) {
        this.playerId = playerId;
        die1 = rollDie();
        die2 = rollDie();
        result = setResult();
    }

    private int rollDie() {
        return new Random().nextInt(6)+1;
    }

    private int setResult () {
        return (die1 + die2) != 7 ? 0 : 1;
    }

}
