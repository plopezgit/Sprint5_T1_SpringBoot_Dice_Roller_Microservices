package com.plopez.diceroller.microservice.player.controller;

import com.plopez.diceroller.microservice.player.model.dto.GameDTO;
import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.service.PlayerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/players")
@SuppressWarnings("unused")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Operation(summary = "It creates a player.")
    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@Valid @RequestBody PlayerDTO playerDTO, WebRequest request) {
        return ResponseEntity.ok(playerService.createPlayer(playerDTO));
    }

    @Operation(summary = "It obtains all the players.")
    @GetMapping
    public ResponseEntity<?> getPlayers() {
        List<PlayerDTO> playersDTO = playerService.getPlayers();
        if (playersDTO.isEmpty()) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .responseCode(HttpStatus.NO_CONTENT.value())
                    .message("The players database is empty.").responseTimeStamp(new Date()).build(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(playersDTO, HttpStatus.OK);
        }
    }

    @Operation(summary = "It creates a game (aka 'play a game') by a specific player.")
    @CircuitBreaker(name="gamesCB", fallbackMethod ="fallbackCreateGameBy")
    @PostMapping("/{playerId}/game")
    public ResponseEntity<GameDTO> createGameBy(@PathVariable int playerId, WebRequest request) {
        return ResponseEntity.ok(playerService.createGameBy(playerId));
    }

    @Operation(summary = "It deleted all the games information of a specific player.")
    @CircuitBreaker(name="gamesCB", fallbackMethod ="fallbackDeleteGamesBy")
    @DeleteMapping("/{playerId}/games")
    public ResponseEntity<ResponseMessage> deleteGamesBy(@PathVariable int playerId, WebRequest request) {
        playerService.deleteGamesBy(playerId);
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.ACCEPTED.value())
                .message("The player games has been deleted.")
                .messageDescription(request.getDescription(false))
                .responseTimeStamp(new Date())
                .build(), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "It obtains all the games of a specific player.")
    @CircuitBreaker(name="gamesCB", fallbackMethod ="fallbackGetGamesByPlayer")
    @GetMapping("/{playerId}/games")
    public ResponseEntity<List<GameDTO>> getGamesByPlayer(@PathVariable int playerId) {
        return ResponseEntity.ok(playerService.getGamesBy(playerId));
    }

    @Operation(summary = "It updates the nickname of a specific player.")
    @PostMapping("update/{id}")
    public ResponseEntity<PlayerDTO> updatePlayerNickname(@PathVariable int id, @Valid @RequestBody PlayerDTO playerDTO, WebRequest request) {
        return ResponseEntity.ok(playerService.updatePlayerNickname(id, playerDTO));
    }

    @Operation(summary = "It updates the game success rate of a specific player.")
    @PostMapping("update/rate/{id}")
    public ResponseEntity<ResponseMessage> updatePlayerSuccessRate(@PathVariable int id, @RequestBody float rate) {
        playerService.updatePlayerSuccessRate(id, rate);
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.ACCEPTED.value())
                .build(), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "It obtains total winning average rate of all players.")
    @GetMapping("/ranking")
    public ResponseEntity<Float> getTotalPlayersWinningAverage() {
        return ResponseEntity.ok(playerService.getTotalPlayersWinningAverage());
    }

    @Operation(summary = "It obtains the player with lowest success rate information.")
    @GetMapping("/ranking/loser")
    public ResponseEntity<Optional<PlayerDTO>> getPlayerMostLoser() {
        return ResponseEntity.ok(playerService.getPlayerMostLoser());
    }

    @Operation(summary = "It obtains the player with highest success rate information.")
    @GetMapping("/ranking/winner")
    public ResponseEntity<Optional<PlayerDTO>>  getPlayerMostWinner() {
        return ResponseEntity.ok(playerService.getPlayerMostWinner());
    }

    public ResponseEntity<ResponseMessage> fallbackCreateGameBy(@PathVariable("playerId") int playerId, RuntimeException e) {
        return new ResponseEntity<>(ResponseMessage.builder().responseCode(HttpStatus.OK.value())
                .message("There is a problem with the Game service. Try again").build(), HttpStatus.OK);
    }

    private ResponseEntity<?> fallbackGetGamesByPlayer(@PathVariable int playerId, RuntimeException e) {
        return new ResponseEntity<>(ResponseMessage.builder().responseCode(HttpStatus.OK.value())
                .message(e.getLocalizedMessage()).build(), HttpStatus.OK);    }

    private ResponseEntity<?> fallbackDeleteGamesBy(@PathVariable int playerId, RuntimeException e) {
        return new ResponseEntity<>(ResponseMessage.builder().responseCode(HttpStatus.OK.value())
                .message("There is a problem with the Game service. Try again").build(), HttpStatus.OK);    }
}
