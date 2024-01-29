package com.plopez.diceroller.microservice.game.model.service;

import com.plopez.diceroller.microservice.game.model.dto.GameDTO;
import com.plopez.diceroller.microservice.game.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.game.model.entity.Game;
import com.plopez.diceroller.microservice.game.model.exception.GameNotFoundException;
import com.plopez.diceroller.microservice.game.model.repository.GameRepository;
import com.plopez.diceroller.microservice.game.model.service.interfaces.GameServiceInterface;
import com.plopez.diceroller.microservice.game.model.service.interfaces.PlayerClientServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
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
        return gameRepository.findAll().stream().map(this::getGameDTOFromEntity).collect(Collectors.toList());
    }

    @Override
    public GameDTO getGameBy(int id) throws GameNotFoundException {
        return gameRepository.findById(id).map(this::getGameDTOFromEntity)
                .orElseThrow(() -> new GameNotFoundException("The game does not exist"));
    }

    @Override
    public void createGameBy(int playerId) {
        gameRepository.save(getGameEntityFromDTO(getTheDiceRolledBy(playerId)));
    }

    @Override
    public void deleteGamesBy(int playerId) {
        gameRepository.deleteGamesByPlayerId(playerId);
    }

    @Override
    public void updatePlayerSuccessRate(int playerId) {
        restTemplate.postForObject("http://player-service/players/update/rate/" + playerId, calculateGamesSuccessRateBy(playerId), PlayerDTO.class);
    }

    @Override
    public List<GameDTO> findGamesByPlayerId(int playerId) {
        return gameRepository.findGamesByPlayerId(playerId).stream().map(this::getGameDTOFromEntity).collect(Collectors.toList());
    }

    private GameDTO getGameDTOFromEntity(Game game) {
        return gameModelMapper.map(game, GameDTO.class);
    }

    private Game getGameEntityFromDTO(GameDTO gameDTO) {
        return gameModelMapper.map(gameDTO, Game.class);
    }

    private float calculateGamesSuccessRateBy(int playerId) {
        return (float) findGamesByPlayerId(playerId).stream().mapToInt(GameDTO::getResult).average().orElse(0);
    }

    private GameDTO getTheDiceRolledBy(int playerID) {
        int die1 = rollDie();
        int die2 = rollDie();
        return GameDTO.builder().playerId(playerID).die1(die1).die2(die2).result(setResult(die1, die2)).build();
    }

    private int rollDie() {
        return new Random().nextInt(6)+1;
    }

    private int setResult (int die1, int die2) { return (die1 + die2) != 7 ? 0 : 1; }

}
