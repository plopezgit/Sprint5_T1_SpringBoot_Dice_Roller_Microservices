package com.plopez.diceroller.microservice.player.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDTO {

    private int id;
    private String nickname;
    private LocalDateTime registrationTimeStamp;
    private float gameSuccessRate;

    public PlayerDTO(String nickname) {
        this.nickname = nickname;
        this.gameSuccessRate = 0;
    }

}
