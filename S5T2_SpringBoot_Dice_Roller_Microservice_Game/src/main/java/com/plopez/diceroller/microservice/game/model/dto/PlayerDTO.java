package com.plopez.diceroller.microservice.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

    private int id;
    private String nickname;
    private LocalDateTime registrationTimeStamp;
    private float gameSuccessRate;

}
