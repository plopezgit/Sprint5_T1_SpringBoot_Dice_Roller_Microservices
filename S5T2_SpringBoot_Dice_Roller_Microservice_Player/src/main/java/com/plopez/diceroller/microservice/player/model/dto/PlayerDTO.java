package com.plopez.diceroller.microservice.player.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Nickname must not be null.")
    private String nickname;
    private LocalDateTime registrationTimeStamp;
    private float gameSuccessRate;

    public PlayerDTO(String nickname) {
        this.nickname = nickname;
    }

}
