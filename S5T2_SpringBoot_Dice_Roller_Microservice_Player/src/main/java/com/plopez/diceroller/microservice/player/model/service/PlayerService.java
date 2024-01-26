package com.plopez.diceroller.microservice.player.model.service;

import com.plopez.diceroller.microservice.player.model.dto.GameDTO;
import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.entity.Player;
import com.plopez.diceroller.microservice.player.model.exception.NickNameAlreadyExistException;
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
    public void createPlayer(PlayerDTO playerDTO) throws NickNameAlreadyExistException {
        PlayerDTO newPlayer;
        if (playerDTO.getNickname().isEmpty() || playerDTO.getNickname().isBlank()) {
            newPlayer = new PlayerDTO("Anonymous");
        } else {
            newPlayer = new PlayerDTO(playerDTO.getNickname());
        }

        if (playerRepository.findByNickname(newPlayer.getNickname()).isPresent()
                && !newPlayer.getNickname().equalsIgnoreCase("Anonymous")) {
            throw new NickNameAlreadyExistException("The nickname already exist, please try another one.");
        }

        playerRepository.save(getPlayerEntityFromDTO(newPlayer));
    }

    @Override
    public void updatePlayer(int id, PlayerDTO playerDTO) throws PlayerNotFoundException {
        PlayerDTO player = getPlayerBy(id);
        player.setNickname(playerDTO.getNickname());
        playerRepository.save(getPlayerEntityFromDTO(player));
    }

    @Override
    public void deletePlayerBy(int id) {
        playerRepository.deleteById(id);
    }

    @Override
    public List<GameDTO> getGamesBy(int playerId) {
        return restTemplate.getForObject("http://game-service/games/player/" + playerId, List.class);
    }

    @Override
    public GameDTO createGameBy(int playerId) {
        return restTemplate.postForObject("http://game-service/games/player/" + playerId, new GameDTO(), GameDTO.class);
    }

    private PlayerDTO getPlayerDTOFromEntity(Player player) {
        return playerModelMapper.map(player, PlayerDTO.class);
    }

    private Player getPlayerEntityFromDTO(PlayerDTO flowerDTO) {
        return playerModelMapper.map(flowerDTO, Player.class);
    }


}
