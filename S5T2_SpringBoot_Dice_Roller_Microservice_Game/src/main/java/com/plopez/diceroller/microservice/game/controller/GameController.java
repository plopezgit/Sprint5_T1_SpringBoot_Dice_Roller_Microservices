package com.plopez.diceroller.microservice.game.controller;

import com.plopez.diceroller.microservice.game.model.dto.GameDTO;
import com.plopez.diceroller.microservice.game.model.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/games")
@SuppressWarnings("unused")
public class GameController {

    @Autowired
    private GameService gameService;

    @Operation(summary = "It creates a game (aka 'play a game') by a specific player.")
    @PostMapping("/{playerId}/player")
    public ResponseEntity<GameDTO> createGameBy(@PathVariable int playerId) {
        GameDTO gameDTO = gameService.createGameBy(playerId);
        gameService.updatePlayerSuccessRate(playerId);
        return ResponseEntity.ok(gameDTO);
    }

    @Operation(summary = "It obtains all the games of a specific player.")
    @GetMapping("/player/{playerId}")
    public ResponseEntity<?> getGamesBy(@PathVariable int playerId) {
        return ResponseEntity.ok(gameService.findGamesByPlayerId(playerId));
    }

    @Operation(summary = "It deleted all the games information of a specific player.")
    @DeleteMapping("{playerId}/delete")
    public ResponseEntity<ResponseMessage> deleteGamesBy(@PathVariable int playerId) {
        gameService.deleteGamesBy(playerId);
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.ACCEPTED.value())
                .message("The player games has been deleted.")
                .build(), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "It obtains all the games information created.")
    @GetMapping
    public ResponseEntity<?> getGames() {
        List<GameDTO> gamesDTO = gameService.getGames();
        if (gamesDTO.isEmpty()) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .responseCode(HttpStatus.NO_CONTENT.value())
                    .message("The games database is empty.").responseTimeStamp(new Date()).build(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(gamesDTO, HttpStatus.OK);
        }
    }

    @Operation(summary = "It obtains the game information by an specific game identification.")
    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGame(@PathVariable int id) {
        return ResponseEntity.ok(gameService.getGameBy(id));
    }
}