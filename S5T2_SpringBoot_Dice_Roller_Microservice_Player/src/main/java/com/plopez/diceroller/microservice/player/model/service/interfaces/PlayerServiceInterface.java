package com.plopez.diceroller.microservice.player.model.service.interfaces;

import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;

import java.util.List;

@SuppressWarnings("unused")
public interface PlayerServiceInterface {

    void createPlayer(PlayerDTO playerDTO);
    void updatePlayerNickname(int id, PlayerDTO playerDTO);
    List<PlayerDTO> getPlayers();
    PlayerDTO getPlayerBy(int id);
}
