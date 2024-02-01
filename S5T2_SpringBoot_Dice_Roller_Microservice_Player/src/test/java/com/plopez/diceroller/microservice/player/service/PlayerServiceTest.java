package com.plopez.diceroller.microservice.player.service;

import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.entity.Player;
import com.plopez.diceroller.microservice.player.model.repository.PlayerRepository;
import com.plopez.diceroller.microservice.player.model.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerServiceUnderTest;
    @Mock
    private PlayerRepository playerRepository;
    @Spy
    private ModelMapper modelMapper;

    private List<Player> players;

    @BeforeEach
    void testSetUp() {
        players = Arrays.asList(new Player(1, "player1", LocalDateTime.now(), 0.2F),
                new Player(2, "player2", LocalDateTime.now(), 0.3F),
                        new Player(3, "player3", LocalDateTime.now(), 0.4F));
    }

    @DisplayName("Given a player list, and players has gameSuccessRate data, when get its total average of success, the average is correct.")
    @Test
    void getTotalPlayersWinningAverageTest() {
        when(playerRepository.findAll()).thenReturn(players);

        float average = playerServiceUnderTest.getTotalPlayersWinningAverage();

        assertThat(players).isNotEmpty();
        assertThat(players.size()).isEqualTo(3);
        assertThat(players.stream().map(Player::getGameSuccessRate).reduce(0f, Float::sum) / players.size())
                .isEqualTo(average);
    }

    @DisplayName("Given a player list, and player has gameSuccessRate data, when get the player most loser, then it is correct.")
    @Test
    void getPlayerMostLoser() {
        when(playerRepository.findAll()).thenReturn(players);

        Optional<PlayerDTO> playerDTO = playerServiceUnderTest.getPlayerMostLoser();

        assertThat(playerDTO).isNotNull();
        assertThat(playerDTO.get().getGameSuccessRate()).isEqualTo(0.2F);
        assertThat(players.stream().min((Comparator.comparing(Player::getGameSuccessRate)))).hasSameHashCodeAs(playerDTO);
    }

    @DisplayName("Given a player list, and player has gameSuccessRate data, when get the player most winner, then it is correct.")
    @Test
    void getPlayerMostWinner() {
        when(playerRepository.findAll()).thenReturn(players);

        Optional<PlayerDTO> playerDTO = playerServiceUnderTest.getPlayerMostWinner();

        assertThat(playerDTO).isNotNull();
        assertThat(playerDTO.get().getGameSuccessRate()).isEqualTo(0.4F);
        assertThat(players.stream().max((Comparator.comparing(Player::getGameSuccessRate)))).hasSameHashCodeAs(playerDTO);
    }


}
