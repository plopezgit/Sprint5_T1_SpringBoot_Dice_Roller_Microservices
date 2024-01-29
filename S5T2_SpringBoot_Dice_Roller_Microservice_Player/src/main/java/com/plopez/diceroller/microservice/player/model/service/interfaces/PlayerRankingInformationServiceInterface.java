package com.plopez.diceroller.microservice.player.model.service.interfaces;

import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.exception.PlayerNotFoundException;

import java.util.Optional;

public interface PlayerRankingInformationServiceInterface extends PlayerServiceInterface {

    void updatePlayerSuccessRate(int playerId, float rate) throws PlayerNotFoundException;
    float getTotalPlayersWinningAverage();
    Optional<PlayerDTO> getPlayerMostLoser();
    Optional<PlayerDTO> getPlayerMostWinner();
}
