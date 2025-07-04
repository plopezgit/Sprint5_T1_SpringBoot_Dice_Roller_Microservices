package com.plopez.diceroller.microservice.game.service;

import com.plopez.diceroller.microservice.game.model.dto.GameDTO;
import com.plopez.diceroller.microservice.game.model.entity.Game;
import com.plopez.diceroller.microservice.game.model.repository.GameRepository;
import com.plopez.diceroller.microservice.game.model.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @InjectMocks
    private GameService gameServiceUnderTest;
    @Mock
    private GameRepository gameRepository;
    @Spy
    private ModelMapper modelMapper;
    private List<Game> games;
    private Game game;
    @BeforeEach
    void testSetUp() {
        games = Arrays.asList(new Game(1, 1, 3, 2, 0),
                new Game(2, 1, 3, 4, 1),
                new Game (3, 2, 3, 3, 0));
        game = new Game (4, 2, 3, 2, 0);
    }

    @DisplayName("Given games created, when getting all the games, then a list of every game information is obtained.")
    @Test
    void getGames() {
        when(gameRepository.findAll()).thenReturn(games);

        List<GameDTO> gamesFound = gameServiceUnderTest.getGames();

        assertThat(gamesFound).isNotEmpty();
    }

    @DisplayName("Given a game created, when getting it by its id, then the game information is obtained.")
    @Test
    void getGameBy() {
        when(gameRepository.findById(4)).thenReturn(Optional.ofNullable(game));

        GameDTO gameFound = gameServiceUnderTest.getGameBy(4);

        assertThat(gameFound).isNotNull();
    }

    //Todo
    @DisplayName("Given a specific player, when creating a game correlated with the player, then the game information is created.")
    @Test
    void createGameBy() {

    }

    //Todo
    @DisplayName("Given a specific player game, when deleting that game correlated with the player, then the game information is deleted.")
    @Test
    void deleteGamesBy() {

    }

    //Todo
    @DisplayName("Given a specific player")
    @Test
    void findGamesByPlayerId() {

    }

}
