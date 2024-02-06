package com.plopez.diceroller.microservice.game.repository;

import com.plopez.diceroller.microservice.game.model.entity.Game;
import com.plopez.diceroller.microservice.game.model.repository.GameRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;
    private Game game;

    @BeforeEach
    void testSetUp() {
        game = new Game(3, 1, 3, 2, 0);
        gameRepository.save(new Game(1, 2, 3, 2, 0));
        gameRepository.save(new Game(2, 2, 4, 3, 1));
    }

    @DisplayName("Given a player, when saving game, the games is created with right values")
    @Test
    @Order(1)
    void createGame() {
        Game savedGame = gameRepository.save(game);
        assertThat(savedGame).isNotNull();
        assertThat(savedGame.getId()).isGreaterThan(0);
        assertThat(savedGame.getDie1()).isNotNegative();
        assertThat(savedGame.getResult()).isBetween(0, 1);
    }

    @DisplayName("Given players created, when find it all as list, then the list contains player information.")
    @Test
    @Order(2)
    void getGames() {
        List<Game> gamesFound = gameRepository.findAll();
        assertThat(gamesFound).isNotEmpty();
        assertThat(gamesFound.size()).isEqualTo(2);
    }

    @DisplayName("Given players created, and a pull of games with that specific player id correlated, when delete it, then the player games information is deleted.")
    @Test
    @Order(3)
    void deleteGamesBy() {
        gameRepository.save(new Game(1, 6, 3, 2, 0));
        gameRepository.save(new Game(2, 6, 4, 3, 1));
        gameRepository.deleteGamesByPlayerId(6);
    }

    @DisplayName("Given a all the specific player games deleted, when find it, then the result is an empty list.")
    @Test
    @Order(4)
    void findGamesByPlayerId () {
        List<Game> gamesFound = gameRepository.findGamesByPlayerId(6);
        assertThat(gamesFound).isEmpty();
    }
}
