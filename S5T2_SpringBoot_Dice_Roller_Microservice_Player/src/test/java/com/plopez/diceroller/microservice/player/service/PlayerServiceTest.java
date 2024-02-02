package com.plopez.diceroller.microservice.player.service;

import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.entity.Player;
import com.plopez.diceroller.microservice.player.model.exception.NickNameAlreadyExistException;
import com.plopez.diceroller.microservice.player.model.exception.PlayerNotFoundException;
import com.plopez.diceroller.microservice.player.model.repository.PlayerRepository;
import com.plopez.diceroller.microservice.player.model.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;


@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerServiceUnderTest;
    @Mock
    private PlayerRepository playerRepository;
    @Spy
    private ModelMapper modelMapper;

    private Player player;
    private PlayerDTO playerDTO;
    private PlayerDTO newAnonymousPlayerDTO;
    private final String NEW_PLAYER_ANONYMOUS_EXPECTED_VALUE = "Anonymous";
    private PlayerDTO newNicknamedPlayerDTO;
    private final String NEW_PLAYER_NICKNAME_EXPECTED_VALUE = "newPlayerWithNickname";
    private List<Player> players;


    @BeforeEach
    void testSetUp() {
        player = new Player(1, "player1", LocalDateTime.now(), 0.2F);
        playerDTO = new PlayerDTO();
        //Todo needs review
        newAnonymousPlayerDTO = PlayerDTO.builder().nickname("").build();
        newNicknamedPlayerDTO = PlayerDTO.builder().nickname(NEW_PLAYER_NICKNAME_EXPECTED_VALUE).build();
        players = Arrays.asList(new Player(2, "player2", LocalDateTime.now(), 0.2F),
                new Player(3, "player3", LocalDateTime.now(), 0.3F),
                        new Player(4, "player4", LocalDateTime.now(), 0.4F));
    }

    @DisplayName("Given a new player, when the new player nickname is full, then it is created with the selected nickname.")
    @Test
    void createNicknamedPlayerTest() {
        when(newNicknamedPlayerDTO).thenReturn(newNicknamedPlayerDTO);

        PlayerDTO newPlayer = playerServiceUnderTest.createPlayer(newNicknamedPlayerDTO);

        assertThat(newPlayer.getNickname()).isNotNull();
        assertThat(newPlayer.getRegistrationTimeStamp()).isBefore(LocalDateTime.now());
        assertThat(newPlayer.getNickname()).isEqualTo(NEW_PLAYER_NICKNAME_EXPECTED_VALUE);
    }

    @DisplayName("Given a new player, when the new player nickname already exist on the database, then NickNameAlreadyExistException is thrown, and the .")
    @Test
    void createDuplicatedNicknamedPlayer_NickNameAlreadyExistExceptionTest() {
        when(playerRepository.existsByNickname(newNicknamedPlayerDTO.getNickname())).thenThrow(NickNameAlreadyExistException.class);

        assertThrows(NickNameAlreadyExistException.class, () -> {
            playerServiceUnderTest.createPlayer(newNicknamedPlayerDTO);
        });

        Mockito.verify(playerRepository, never()).save(any());
    }

    @DisplayName("Given a new player, when the new player nickname is empty, then it is created as 'Anonymous'.")
    @Test
    void createAnonymousPlayerTest() {
        when(newAnonymousPlayerDTO).thenReturn(newAnonymousPlayerDTO);

        PlayerDTO newPlayer = playerServiceUnderTest.createPlayer(newAnonymousPlayerDTO);

        assertThat(newPlayer.getNickname()).isNotNull();
        assertThat(newPlayer.getRegistrationTimeStamp()).isBefore(LocalDateTime.now());
        assertThat(newPlayer.getNickname()).isEqualTo(NEW_PLAYER_ANONYMOUS_EXPECTED_VALUE);
    }

    @DisplayName("Given a specific player, when the nickname is updated, then the specific player attribute is updated.")
    @Test
    void updatePlayerNicknameTest() {
        when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(player));

        playerDTO.setNickname("newNickname");
        PlayerDTO playerUpdated = playerServiceUnderTest.updatePlayerNickname(1, playerDTO);

        assertThat(playerUpdated.getNickname()).isNotNull();
        assertThat(playerUpdated.getGameSuccessRate()).isEqualTo(player.getGameSuccessRate());
    }

    @DisplayName("Given a player list, when consult the list, the player are listed.")
    @Test
    void getPlayersTest() {
        when(playerRepository.findAll()).thenReturn(players);

        List<PlayerDTO> players = playerServiceUnderTest.getPlayers();

        assertThat(players).isNotEmpty();
    }

    @DisplayName("Given a player list, when consult an specific player by its id, and the id does not exist, then PlayerNotFoundException is thrown.")
    @Test
    void getPlayer_PlayerNorFoundExceptionTest() {
        when(playerRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(PlayerNotFoundException.class, () -> {
            playerServiceUnderTest.getPlayerBy(anyInt());
        });
    }

    @DisplayName("Given a specific player, when a success rate is updated, then player attribute is updated.")
    @Test
    void updatePlayerSuccessRateTest() {
        when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(player));

        float previousRate = player.getGameSuccessRate();
        PlayerDTO playerUpdated = playerServiceUnderTest.updatePlayerSuccessRate(1, 0.6F);

        assertThat(playerUpdated.getGameSuccessRate()).isNotNull();
        assertThat(playerUpdated.getGameSuccessRate()).isNotEqualTo(previousRate);
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
    void getPlayerMostLoserTest() {
        when(playerRepository.findAll()).thenReturn(players);

        Optional<PlayerDTO> playerDTO = playerServiceUnderTest.getPlayerMostLoser();

        assertThat(playerDTO).isNotNull();
        assertThat(playerDTO.get().getGameSuccessRate()).isEqualTo(0.2F);
        assertThat(players.stream().min((Comparator.comparing(Player::getGameSuccessRate)))).hasSameHashCodeAs(playerDTO);
    }

    @DisplayName("Given a player list, and player has gameSuccessRate data, when get the player most winner, then it is correct.")
    @Test
    void getPlayerMostWinnerTest() {
        when(playerRepository.findAll()).thenReturn(players);

        Optional<PlayerDTO> playerDTO = playerServiceUnderTest.getPlayerMostWinner();

        assertThat(playerDTO).isNotNull();
        assertThat(playerDTO.get().getGameSuccessRate()).isEqualTo(0.4F);
        assertThat(players.stream().max((Comparator.comparing(Player::getGameSuccessRate)))).hasSameHashCodeAs(playerDTO);
    }
}
