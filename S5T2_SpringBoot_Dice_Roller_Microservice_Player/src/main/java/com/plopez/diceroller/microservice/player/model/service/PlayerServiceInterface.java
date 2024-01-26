package com.plopez.diceroller.microservice.player.model.service;

import com.plopez.diceroller.microservice.player.model.dto.GameDTO;
import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.exception.NickNameAlreadyExistException;
import com.plopez.diceroller.microservice.player.model.exception.PlayerNotFoundException;

import java.util.List;

@SuppressWarnings("unused")
public interface PlayerServiceInterface {

    List<PlayerDTO> getPlayers();
    PlayerDTO getPlayerBy(int id) throws PlayerNotFoundException;
    void createPlayer(PlayerDTO playerDTO) throws NickNameAlreadyExistException;
    void updatePlayer(int id, PlayerDTO playerDTO) throws PlayerNotFoundException;
    void deletePlayerBy(int id);
    List<GameDTO> getGamesBy(int playerId);
    GameDTO createGameBy (int playerId);
}
