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
    private byte die1;
    private byte die2;
    private byte result;

    public GameDTO(int playerId) {
        this.playerId = playerId;
        die1 = rollDie();
        die2 = rollDie();
        result = setResult();
    }

    private byte rollDie() {
        Random random = new Random();
        byte die = (byte) (random.nextInt(6)+1);
        return die;
    }

    public byte setResult () {
        if ((die1 + die2)!=7) {
            return 0;
        } else {
            return 1;
        }
    }

}
