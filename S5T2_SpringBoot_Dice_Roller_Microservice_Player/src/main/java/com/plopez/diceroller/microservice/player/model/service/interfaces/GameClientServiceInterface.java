package com.plopez.diceroller.microservice.player.model.service.interfaces;

import com.plopez.diceroller.microservice.player.model.dto.GameDTO;

import java.util.List;

@SuppressWarnings("unused")
public interface GameClientServiceInterface {
    List<GameDTO> getGamesBy(int playerId);
    GameDTO createGameBy (int playerId);
    void deleteGamesBy(int playerId);
}
