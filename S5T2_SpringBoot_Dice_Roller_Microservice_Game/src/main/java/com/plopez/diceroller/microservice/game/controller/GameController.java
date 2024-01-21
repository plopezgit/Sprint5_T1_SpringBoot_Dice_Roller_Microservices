package com.plopez.diceroller.microservice.game.controller;

import com.plopez.diceroller.microservice.game.model.dto.GameDTO;
import com.plopez.diceroller.microservice.game.model.exception.GameNotFoundException;
import com.plopez.diceroller.microservice.game.model.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@SuppressWarnings("unused")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<?> getPlayers() {
        List<GameDTO> gamesDTO = gameService.getGames();
        if (gamesDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(gamesDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable int id) {
        try {
            return ResponseEntity.ok(gameService.getGameBy(id));
        } catch (GameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody GameDTO gameDTO) {
        gameService.createGame(gameDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable int id, @RequestBody GameDTO gameDTO) {
        try {
            gameService.updateGame(id, gameDTO);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (GameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable int id) {
        gameService.deleteGameBy(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<?> getGamesByPlayerId(@PathVariable int playerId) {
        List<GameDTO> gamesDTO = gameService.findGamesByPlayerId(playerId);
        if (gamesDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(gamesDTO);
        }
    }

}
