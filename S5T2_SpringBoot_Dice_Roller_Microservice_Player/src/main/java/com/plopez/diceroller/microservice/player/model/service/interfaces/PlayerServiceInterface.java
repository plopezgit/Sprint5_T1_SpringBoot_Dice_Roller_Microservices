package com.plopez.diceroller.microservice.player.model.service.interfaces;

import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.exception.NickNameAlreadyExistException;
import com.plopez.diceroller.microservice.player.model.exception.PlayerNotFoundException;

import java.util.List;

@SuppressWarnings("unused")
public interface PlayerServiceInterface {

    void createPlayer(PlayerDTO playerDTO) throws NickNameAlreadyExistException;
    void updatePlayerNickname(int id, PlayerDTO playerDTO) throws PlayerNotFoundException;
    List<PlayerDTO> getPlayers();
    PlayerDTO getPlayerBy(int id) throws PlayerNotFoundException;
    void deletePlayerBy(int id);
}
