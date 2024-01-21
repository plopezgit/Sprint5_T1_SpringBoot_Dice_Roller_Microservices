package com.plopez.diceroller.microservice.game.model.service;

import com.plopez.diceroller.microservice.game.model.dto.GameDTO;
import com.plopez.diceroller.microservice.game.model.exception.GameNotFoundException;

import java.util.List;

@SuppressWarnings("unused")
public interface GameServiceInterface {

    List<GameDTO> getGames();
    GameDTO getGameBy(int id) throws GameNotFoundException;
    void createGame(GameDTO gameDTO);
    void updateGame(int id, GameDTO playerDTO) throws GameNotFoundException;
    void deleteGameBy(int id);
    List<GameDTO> findGamesByPlayerId (int playerId) throws GameNotFoundException;
}
