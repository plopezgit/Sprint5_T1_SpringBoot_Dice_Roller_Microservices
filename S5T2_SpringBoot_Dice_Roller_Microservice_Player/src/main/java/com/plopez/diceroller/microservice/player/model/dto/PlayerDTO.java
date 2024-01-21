package com.plopez.diceroller.microservice.player.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

    private int id;
    private String nickname;
    private float gameSuccessRate;

}
