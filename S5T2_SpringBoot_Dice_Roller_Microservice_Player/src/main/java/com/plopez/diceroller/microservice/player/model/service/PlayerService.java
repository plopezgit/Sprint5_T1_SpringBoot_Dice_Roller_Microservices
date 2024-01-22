package com.plopez.diceroller.microservice.player.model.service;

import com.plopez.diceroller.microservice.player.model.dto.GameDTO;
import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.entity.Player;
import com.plopez.diceroller.microservice.player.model.exception.PlayerNotFoundException;
import com.plopez.diceroller.microservice.player.model.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService implements PlayerServiceInterface {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ModelMapper playerModelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<PlayerDTO> getPlayers() {
        List<Player> players = playerRepository.findAll();
        return players.stream().map(this::getPlayerDTOFromEntity).collect(Collectors.toList());
    }

    @Override
    public PlayerDTO getPlayerBy(int id) throws PlayerNotFoundException {
        return playerRepository.findById(id).map(this::getPlayerDTOFromEntity)
                .orElseThrow(() -> new PlayerNotFoundException("The game does not exist"));
    }

    @Override
    public void createPlayer(PlayerDTO playerDTO) {
        playerRepository.save(getPlayerEntityFromDTO(playerDTO));
    }

    @Override
    public void updatePlayer(int id, PlayerDTO playerDTO) throws PlayerNotFoundException {
        PlayerDTO player = getPlayerBy(id);
        player.setNickname(playerDTO.getNickname());
        player.setGameSuccessRate(playerDTO.getGameSuccessRate());
        playerRepository.save(getPlayerEntityFromDTO(player));
    }

    @Override
    public void deletePlayerBy(int id) {
        playerRepository.deleteById(id);
    }

    @Override
    public List<GameDTO> getGamesBy(int playerId) {
        List<GameDTO> games = restTemplate.getForObject("http://game-service/games/player/" + playerId, List.class);
        return games;
    }

    private PlayerDTO getPlayerDTOFromEntity(Player player) {
        return playerModelMapper.map(player, PlayerDTO.class);
    }

    private Player getPlayerEntityFromDTO(PlayerDTO flowerDTO) {
        return playerModelMapper.map(flowerDTO, Player.class);
    }

}
