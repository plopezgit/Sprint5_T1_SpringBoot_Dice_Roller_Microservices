package com.plopez.diceroller.microservice.player.model.service.interfaces;

import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;

public interface PlayerRankingInformationServiceInterface extends PlayerServiceInterface {
    float getTotalPlayersWinningAverage();
    PlayerDTO getPlayerMostLoser();
    PlayerDTO getPlayerMostWinner();
}
