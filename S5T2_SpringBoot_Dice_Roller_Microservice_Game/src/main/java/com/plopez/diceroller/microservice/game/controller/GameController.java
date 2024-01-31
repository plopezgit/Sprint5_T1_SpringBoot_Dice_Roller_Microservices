package com.plopez.diceroller.microservice.game.controller;

import com.plopez.diceroller.microservice.game.model.dto.GameDTO;
import com.plopez.diceroller.microservice.game.model.service.GameService;
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

    @PostMapping("/{playerId}/player")
    public ResponseEntity<GameDTO> createGameBy(@PathVariable int playerId) {
        GameDTO gameDTO = gameService.createGameBy(playerId);
        gameService.updatePlayerSuccessRate(playerId);
        return ResponseEntity.ok(gameDTO);
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<?> getGamesBy(@PathVariable int playerId) {
        List<GameDTO> gamesDTO = gameService.findGamesByPlayerId(playerId);
        if (gamesDTO.isEmpty()) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .responseCode(HttpStatus.NO_CONTENT.value())
                    .message("The games database is empty.").responseTimeStamp(new Date()).build(), HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(gamesDTO);
        }
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<ResponseMessage> deleteGamesBy(@PathVariable int playerId) {
        gameService.deleteGamesBy(playerId);
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.ACCEPTED.value())
                .message("The player games has been deleted.")
                .build(), HttpStatus.ACCEPTED);
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGame(@PathVariable int id) {
        return ResponseEntity.ok(gameService.getGameBy(id));
    }




}