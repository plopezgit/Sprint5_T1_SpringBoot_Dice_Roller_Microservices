package com.plopez.diceroller.microservice.player.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {

    private byte die1;
    private byte die2;
    private String result;

}
