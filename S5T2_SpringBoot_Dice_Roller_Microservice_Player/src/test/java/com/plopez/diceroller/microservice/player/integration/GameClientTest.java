package com.plopez.diceroller.microservice.player.integration;

import com.plopez.diceroller.microservice.player.model.dto.GameDTO;
import com.plopez.diceroller.microservice.player.model.service.PlayerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class GameClientTest {

    private GameDTO game;
    @Autowired
    private PlayerService playerService;

    @DisplayName("Given a pull of player, when it creates a game, then the games are created, and its id is related to the game.")
    @Order(1)
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void createGameByPlayerTest(int playerID) {
        game = playerService.createGameBy(playerID);
        assertThat(game).isNotNull();
        assertThat(game.getPlayerId()).isEqualTo(playerID);
    }

    @DisplayName("Given a pull of players, when deletes its games, the request is accepted, and the games are deleted for each player.")
    @Order(2)
    @ParameterizedTest
    @ValueSource(ints = {10, 11})
    void deleteGamesBy(int playerId) {
        ResponseEntity<Void> response = playerService.deleteGamesBy(playerId);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }
}
