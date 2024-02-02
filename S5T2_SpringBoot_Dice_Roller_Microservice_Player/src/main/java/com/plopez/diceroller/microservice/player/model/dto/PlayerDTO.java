package com.plopez.diceroller.microservice.player.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @NotNull(message = "Id must not be null.")
    @JsonIgnore
    private int id;
    @NotNull(message = "Nickname must not be null.")
    private String nickname;
    private LocalDateTime registrationTimeStamp;
    private float gameSuccessRate;
}
