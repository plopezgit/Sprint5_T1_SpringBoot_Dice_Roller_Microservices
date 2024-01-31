package com.plopez.diceroller.microservice.game.model.service.interfaces;

import com.plopez.diceroller.microservice.game.model.dto.GameDTO;
import com.plopez.diceroller.microservice.game.model.exception.GameNotFoundException;

import java.util.List;

@SuppressWarnings("unused")
public interface GameServiceInterface {

    List<GameDTO> getGames();
    GameDTO getGameBy(int id) throws GameNotFoundException;
    void deleteGamesBy(int playerId);
    GameDTO createGameBy(int playerId);
    List<GameDTO> findGamesByPlayerId (int playerId) throws GameNotFoundException;
}
