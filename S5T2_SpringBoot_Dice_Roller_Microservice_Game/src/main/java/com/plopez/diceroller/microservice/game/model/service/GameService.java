package com.plopez.diceroller.microservice.game.model.service;

import com.plopez.diceroller.microservice.game.model.dto.GameDTO;
import com.plopez.diceroller.microservice.game.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.game.model.entity.Game;
import com.plopez.diceroller.microservice.game.model.exception.GameNotFoundException;
import com.plopez.diceroller.microservice.game.model.repository.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class GameService implements GameServiceInterface, PlayerClientServiceInterface {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ModelMapper gameModelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<GameDTO> getGames() {
        List<Game> players = gameRepository.findAll();
        return players.stream().map(this::getGameDTOFromEntity).collect(Collectors.toList());
    }

    @Override
    public GameDTO getGameBy(int id) throws GameNotFoundException {
        return gameRepository.findById(id).map(this::getGameDTOFromEntity)
                .orElseThrow(() -> new GameNotFoundException("The game does not exist"));
    }

    @Override
    public void createGameBy(int playerId) {
        gameRepository.save(getGameEntityFromDTO(new GameDTO(playerId)));
    }

    @Override
    public void deleteGamesBy(int playerId) {
        gameRepository.deleteGamesByPlayerId(playerId);
    }

    @Override
    public void updatePlayerSuccessRate(int playerId) {
        List<GameDTO> games = findGamesByPlayerId(playerId);
        float rate = (float) games.stream()
                .mapToInt(GameDTO::getResult)
                .sum() / games.size();
        restTemplate.postForObject("http://player-service/players/update/rate/" + playerId, rate, PlayerDTO.class);
    }

    @Override
    public List<GameDTO> findGamesByPlayerId(int playerId) {
        List<Game> games = gameRepository.findGamesByPlayerId(playerId);
        return games.stream().map(this::getGameDTOFromEntity).collect(Collectors.toList());
    }

    private GameDTO getGameDTOFromEntity(Game game) {
        return gameModelMapper.map(game, GameDTO.class);
    }

    private Game getGameEntityFromDTO(GameDTO gameDTO) {
        return gameModelMapper.map(gameDTO, Game.class);
    }


}
