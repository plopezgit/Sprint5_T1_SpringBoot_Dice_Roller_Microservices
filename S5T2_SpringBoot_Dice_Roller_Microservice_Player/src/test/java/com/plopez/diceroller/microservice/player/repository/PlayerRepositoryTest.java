package com.plopez.diceroller.microservice.player.repository;

import com.plopez.diceroller.microservice.player.model.entity.Player;
import com.plopez.diceroller.microservice.player.model.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;
    private Player newPlayer;
    private Player newPlayer2;


    @BeforeEach
    void testSetUp() {
        //Todo needs review
        String playerNickname = "testPlayer";
        //Todo needs review
        newPlayer = playerRepository.save(Player.builder().nickname(playerNickname).build());
        String playerNickname2 = "testPlayer2";
        //Todo needs review
        newPlayer2 = playerRepository.save(Player.builder().nickname(playerNickname2).build());
    }

    @DisplayName("Given a new Player, when save, the player is saved on database.")
    @Test
    void createPlayerTest() {
        //Todo needs review
        Player savedPlayer = playerRepository.save(Player.builder().nickname("brutus").build());
        assertThat(savedPlayer).isNotNull();
        assertThat(savedPlayer.getId()).isGreaterThan(0);
        assertThat(savedPlayer.getNickname()).isEqualTo("brutus");
        assertThat(savedPlayer.getGameSuccessRate()).isEqualTo(0.0F);
    }

    @DisplayName("Given a Player, when update its nickname, then the player is stored with the new nickname")
    @Test
    void updatePlayerNicknameTest() {
        String UPDATED_NICKNAME = "testPlayerUpdated";
        newPlayer2.setNickname(UPDATED_NICKNAME);
        Player updatedPlayer = playerRepository.save(newPlayer2);
        assertThat(updatedPlayer).isNotNull();
        assertThat(updatedPlayer.getNickname()).isEqualTo(UPDATED_NICKNAME);
    }

    @DisplayName("Given a Player is created, when find all player, then get it listed.")
    @Test
    void findAllPlayersTest() {
        List<Player> playersFound = playerRepository.findAll();
        assertThat(playersFound).isNotNull();
        assertThat(playersFound.size()).isEqualTo(2);
    }

    @DisplayName("Given a Player is created, when find it by ID, then get it.")
    @Test
    void findOnePlayerTest() {
        int playerId = newPlayer.getId();
        Player playerFound = playerRepository.findById(playerId).get();
        assertThat(playerFound).isNotNull();
        assertThat(playerFound.getId()).isEqualTo(playerId);
    }

    @DisplayName("Given a Player is created, when validate if it exist by its id, then get the right boolean.")
    @Test
    void existPlayerByIdTest() {
        Boolean exist = playerRepository.existsById(newPlayer2.getId());
        assertThat(exist).isTrue();
    }

    @DisplayName("Given a Player is created, when validate if it exist by its Nickname, then get the right boolean.")
    @Test
    void existPlayerByNicknameTest() {
        Boolean exist = playerRepository.existsByNickname(newPlayer2.getNickname());
        assertThat(exist).isTrue();
    }

}
