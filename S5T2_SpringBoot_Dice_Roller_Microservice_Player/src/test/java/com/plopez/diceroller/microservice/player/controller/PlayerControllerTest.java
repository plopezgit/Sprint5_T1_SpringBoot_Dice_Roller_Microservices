package com.plopez.diceroller.microservice.player.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Autowired
    private ObjectMapper objectMapper;
    private PlayerDTO playerDTO;
    private PlayerDTO newNicknamedPlayerDTO;
    private final String NEW_PLAYER_NICKNAME_EXPECTED_VALUE = "newPlayerWithNickname";
    private List<PlayerDTO> players;


    @BeforeEach
    void testSetUp() {
        playerDTO = new PlayerDTO(1, "player1", LocalDateTime.now(), 0.2F);
        //Todo needs review
        newNicknamedPlayerDTO = PlayerDTO.builder().nickname(NEW_PLAYER_NICKNAME_EXPECTED_VALUE).registrationTimeStamp(LocalDateTime.now()).gameSuccessRate(0.0F).build();
        players = Arrays.asList(new PlayerDTO(2, "player2", LocalDateTime.now(), 0.2F),
                new PlayerDTO(3, "player3", LocalDateTime.now(), 0.3F),
                new PlayerDTO(4, "player4", LocalDateTime.now(), 0.4F));
    }

    @DisplayName("Given a create player request, when create, then the response contains player attributes")
    @Test
    void createPLayerTest() throws Exception {
        given(playerService.createPlayer(newNicknamedPlayerDTO))
                .willReturn(newNicknamedPlayerDTO);

        ResultActions response = mockMvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newNicknamedPlayerDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(newNicknamedPlayerDTO.getNickname().toString()))
                .andExpect(jsonPath("$.registrationTimeStamp").value(newNicknamedPlayerDTO.getRegistrationTimeStamp().toString()))
                .andExpect(jsonPath("$.gameSuccessRate").value(newNicknamedPlayerDTO.getGameSuccessRate()));
    }

    //Todo
    @DisplayName("Given a player list, when get it, then the response contains the players.")
    @Test
    void getPlayersTest() throws Exception {
        given(playerService.getPlayers())
                .willReturn(players);

        ResultActions response = mockMvc.perform(get("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(players)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(players.size()))
                .andExpect(jsonPath("$.[0].nickname").value(players.get(0).getNickname()));
    }

    //Todo
    @DisplayName("Given a player, when updated its nickname, then the response contains the player with the nickname updated.")
    @Test
    void updatePlayerNicknameTest() {

    }

    @DisplayName("Given players, when get total average of its success rate, then the response contains a the average.")
    @Test
    void getTotalPlayersWinningAverageTest() throws Exception {
        given(playerService.getTotalPlayersWinningAverage())
                .willReturn(0.5F);

        ResultActions response = mockMvc.perform(get("/players/ranking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Float.class)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0.5));
    }

    @DisplayName("Given players, when get worst success rate player, then the response contains the player.")
    @Test
    void getPlayerMostLoserTest() throws Exception {
        given(playerService.getPlayerMostLoser())
                .willReturn(Optional.ofNullable(playerDTO));

        ResultActions response = mockMvc.perform(get("/players/ranking/loser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playerDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(playerDTO.getNickname().toString()))
                .andExpect(jsonPath("$.registrationTimeStamp").value(playerDTO.getRegistrationTimeStamp().toString()))
                .andExpect(jsonPath("$.gameSuccessRate").value(playerDTO.getGameSuccessRate()));
    }

    @DisplayName("Given players, when get best success rate player, then the response contains the player.")
    @Test
    void getPlayerMostWinnerTest() throws Exception {
        given(playerService.getPlayerMostWinner())
                .willReturn(Optional.ofNullable(playerDTO));

        ResultActions response = mockMvc.perform(get("/players/ranking/winner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playerDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(playerDTO.getNickname().toString()))
                .andExpect(jsonPath("$.registrationTimeStamp").value(playerDTO.getRegistrationTimeStamp().toString()))
                .andExpect(jsonPath("$.gameSuccessRate").value(playerDTO.getGameSuccessRate()));
    }

    //Todo
    void createGameByTest() {

    }

    //Todo
    void deleteGamesBy() {

    }
}
