package com.plopez.diceroller.microservice.game.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDTO {

    @Schema(description = "Entity identification.", example = "1")
    private int id;
    @Schema(description = "Entity player correlated identification attribute.", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int playerId;
    @Schema(description = "Die one roll information attribute randomly generated.", example = "1-6")
    private int die1;
    @Schema(description = "Die two roll information attribute randomly generated.", example = "1-6")
    private int die2;
    @Schema(description = "Result information attribute based on the sum of dice information. 0 equal to Lose. 1 equal to Win.", example = "0-1")
    private int result;
}
