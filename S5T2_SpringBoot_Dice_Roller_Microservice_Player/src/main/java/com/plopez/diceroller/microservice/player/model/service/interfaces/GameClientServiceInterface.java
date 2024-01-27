package com.plopez.diceroller.microservice.player.model.service.interfaces;

import com.plopez.diceroller.microservice.player.model.dto.GameDTO;
import com.plopez.diceroller.microservice.player.model.exception.PlayerNotFoundException;

import java.util.List;

public interface GameClientServiceInterface {
    List<GameDTO> getGamesBy(int playerId);
    GameDTO createGameBy (int playerId) throws PlayerNotFoundException;
    void deleteGamesBy(int playerId);
}
