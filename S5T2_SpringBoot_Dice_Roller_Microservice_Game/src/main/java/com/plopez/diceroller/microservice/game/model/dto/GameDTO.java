package com.plopez.diceroller.microservice.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
        Random random = new Random();
        int die = (random.nextInt(6)+1);
        return die;
    }

    public int setResult () {
        if ((die1 + die2)!=7) {
            return 0;
        } else {
            return 1;
        }
    }

}
