package com.plopez.diceroller.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
public class Game {

    private int id;
    private int playerId;
    private int die1;
    private int die2;
    private int result;

    public int getId() {
        return id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getDie1() {
        return die1;
    }

    public int getDie2() {
        return die2;
    }

    public int getResult() {
        return result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setDie1(int die1) {
        this.die1 = die1;
    }

    public void setDie2(int die2) {
        this.die2 = die2;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Game{" +
                "playerId=" + playerId +
                ", die1=" + die1 +
                ", die2=" + die2 +
                ", result=" + result +
                '}';
    }
}
