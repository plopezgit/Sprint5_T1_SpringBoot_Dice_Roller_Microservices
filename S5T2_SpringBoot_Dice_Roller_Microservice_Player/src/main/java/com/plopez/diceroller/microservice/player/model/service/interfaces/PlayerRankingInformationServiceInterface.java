package com.plopez.diceroller.microservice.player.model.service.interfaces;

import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;

import java.util.Optional;

public interface PlayerRankingInformationServiceInterface extends PlayerServiceInterface {
    float getTotalPlayersWinningAverage();
    Optional<PlayerDTO> getPlayerMostLoser();
    Optional<PlayerDTO> getPlayerMostWinner();
}
