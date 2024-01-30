package com.plopez.diceroller.microservice.player.controller;

import com.plopez.diceroller.microservice.player.model.dto.GameDTO;
import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.service.PlayerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@SuppressWarnings("unused")
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
        return ResponseEntity.ok(playerService.getPlayerBy(id));
    }

    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody PlayerDTO playerDTO) {
        playerService.createPlayer(playerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<?> updatePlayerNickname(@PathVariable int id, @RequestBody PlayerDTO playerDTO) {
        playerService.updatePlayerNickname(id, playerDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("update/rate/{id}")
    public ResponseEntity<?> updatePlayerSuccessRate(@PathVariable int id, @RequestBody float rate) {
        playerService.updatePlayerSuccessRate(id, rate);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/ranking")
    public ResponseEntity<?> getTotalPlayersWinningAverage() {
        return ResponseEntity.ok(playerService.getTotalPlayersWinningAverage());
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<?> getPlayerMostLoser() {
        return ResponseEntity.ok(playerService.getPlayerMostLoser());
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<?> getPlayerMostWinner() {
        return ResponseEntity.ok(playerService.getPlayerMostWinner());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable int id) {
        playerService.deletePlayerBy(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @CircuitBreaker(name="gamesCB", fallbackMethod ="fallbackCreateGameBy")
    @PostMapping("/{playerId}/game")
    public ResponseEntity<?> createGameBy(@PathVariable int playerId) {
        return ResponseEntity.ok(playerService.createGameBy(playerId));
    }

    @CircuitBreaker(name="gamesCB", fallbackMethod ="fallbackDeleteGamesBy")
    @DeleteMapping("/{id}/games")
    public ResponseEntity<?> deleteGamesBy(@PathVariable int id) {
        playerService.deleteGamesBy(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @CircuitBreaker(name="gamesCB", fallbackMethod ="fallbackGetGamesByPlayer")
    @GetMapping("/{playerId}/games")
    public ResponseEntity<?> getGamesByPlayer(@PathVariable int playerId) {
        List<GameDTO> games = playerService.getGamesBy(playerId);
        return ResponseEntity.ok(games);
    }

    public ResponseEntity<?> fallbackCreateGameBy(@PathVariable("playerId") int playerId, RuntimeException e) {
        return new ResponseEntity<>("The player: " + playerId + " can not play now. Try later", HttpStatus.OK);
    }

    private ResponseEntity<?> fallbackGetGamesByPlayer(@PathVariable int playerId, RuntimeException e) {
        return new ResponseEntity<>("The player: " + playerId + " does not have games yet,does not have games yet, or game delete service is not available now.", HttpStatus.OK);
    }

    private ResponseEntity<?> fallbackDeleteGamesBy(@PathVariable int playerId, RuntimeException e) {
        return new ResponseEntity<>("The player: " + playerId + " does not have games yet, or game delete service is not available now.", HttpStatus.OK);
    }
}
