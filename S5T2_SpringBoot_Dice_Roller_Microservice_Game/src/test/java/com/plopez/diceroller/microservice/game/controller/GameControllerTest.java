package com.plopez.diceroller.microservice.game.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plopez.diceroller.microservice.game.model.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

//Todo
@WebMvcTest
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GameService gameService;
    @Autowired
    ObjectMapper objectMapper;



}
