package com.plopez.diceroller.microservice.player.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {

    private byte die1;
    private byte die2;
    private byte result;
    private int playerId;

    public GameDTO(int playerId) {
        die1 = rollDie();
        die2 = rollDie();
        result = setResult();
        this.playerId = playerId;
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
