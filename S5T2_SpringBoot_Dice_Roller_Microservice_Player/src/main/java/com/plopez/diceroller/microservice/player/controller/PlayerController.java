package com.plopez.diceroller.microservice.player.controller;

import com.plopez.diceroller.microservice.player.model.dto.GameDTO;
import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.exception.PlayerNotFoundException;
import com.plopez.diceroller.microservice.player.model.service.PlayerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<?> getPlayers() {
        List<PlayerDTO> playersDTO = playerService.getPlayers();
        if (playersDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(playersDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable int id) {
        try {
            return ResponseEntity.ok(playerService.getPlayerBy(id));
        } catch (PlayerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody PlayerDTO playerDTO) {
        playerService.createPlayer(playerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable int id, @RequestBody PlayerDTO playerDTO) {
        try {
            playerService.updatePlayer(id, playerDTO);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (PlayerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable int id) {
        playerService.deletePlayerBy(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @CircuitBreaker(name="gamesCB", fallbackMethod ="fallbackGetGamesByPlayer")
    @GetMapping("/{playerId}/games")
    public ResponseEntity<?> getGamesByPlayer(@PathVariable int playerId) {
        List<GameDTO> games = playerService.getGamesBy(playerId);
        return ResponseEntity.ok(games);
    }

    private ResponseEntity<?> fallbackGetGamesByPlayer(@PathVariable int playerId, RuntimeException e) {
        return new ResponseEntity<>("The player: " + playerId + " does not have games yet.", HttpStatus.OK);
    }
}
