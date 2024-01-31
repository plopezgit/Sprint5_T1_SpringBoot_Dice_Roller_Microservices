package com.plopez.diceroller.microservice.player.controller;

import com.plopez.diceroller.microservice.player.model.dto.GameDTO;
import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.service.PlayerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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

    @PostMapping
    public ResponseEntity<ResponseMessage> createPlayer(@Valid @RequestBody PlayerDTO playerDTO, WebRequest request) {
        playerService.createPlayer(playerDTO);
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.CREATED.value())
                .message("The player has been created: " + playerDTO)
                .messageDescription(request.getDescription(false))
                .responseTimeStamp(new Date())
                .build(), HttpStatus.CREATED);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<ResponseMessage> updatePlayerNickname(@PathVariable int id, @Valid @RequestBody PlayerDTO playerDTO, WebRequest request) {
        playerService.updatePlayerNickname(id, playerDTO);
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.ACCEPTED.value())
                .message("The player nickname has been updated.")
                .messageDescription(request.getDescription(false))
                .responseTimeStamp(new Date())
                .build(), HttpStatus.ACCEPTED);
    }

    @PostMapping("update/rate/{id}")
    public ResponseEntity<ResponseMessage> updatePlayerSuccessRate(@PathVariable int id, @RequestBody float rate) {
        playerService.updatePlayerSuccessRate(id, rate);
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.ACCEPTED.value())
                .build(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/ranking")
    public ResponseEntity<Float> getTotalPlayersWinningAverage() {
        return ResponseEntity.ok(playerService.getTotalPlayersWinningAverage());
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<Optional<PlayerDTO>> getPlayerMostLoser() {
        return ResponseEntity.ok(playerService.getPlayerMostLoser());
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<Optional<PlayerDTO>>  getPlayerMostWinner() {
        return ResponseEntity.ok(playerService.getPlayerMostWinner());
    }

    @CircuitBreaker(name="gamesCB", fallbackMethod ="fallbackCreateGameBy")
    @PostMapping("/{playerId}/game")
    public ResponseEntity<GameDTO> createGameBy(@PathVariable int playerId, WebRequest request) {
        return ResponseEntity.ok(playerService.createGameBy(playerId));
    }

    @CircuitBreaker(name="gamesCB", fallbackMethod ="fallbackDeleteGamesBy")
    @DeleteMapping("/{id}/games")
    public ResponseEntity<?> deleteGamesBy(@PathVariable int id, WebRequest request) {
        playerService.deleteGamesBy(id);
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.ACCEPTED.value())
                .message("The player games has been deleted.")
                .messageDescription(request.getDescription(false))
                .responseTimeStamp(new Date())
                .build(), HttpStatus.ACCEPTED);
    }

    @CircuitBreaker(name="gamesCB", fallbackMethod ="fallbackGetGamesByPlayer")
    @GetMapping("/{playerId}/games")
    public ResponseEntity<List<GameDTO>> getGamesByPlayer(@PathVariable int playerId) {
        List<GameDTO> games = playerService.getGamesBy(playerId);
        return ResponseEntity.ok(games);
    }

    public ResponseEntity<ResponseMessage> fallbackCreateGameBy(@PathVariable("playerId") int playerId, RuntimeException e) {
        return new ResponseEntity<>(ResponseMessage.builder().responseCode(HttpStatus.OK.value())
                .message("There is a problem with the Game service. Try again").build(), HttpStatus.OK);
    }

    private ResponseEntity<?> fallbackGetGamesByPlayer(@PathVariable int playerId, RuntimeException e) {
        return new ResponseEntity<>(ResponseMessage.builder().responseCode(HttpStatus.OK.value())
                .message("There is a problem with the Game service. Try again").build(), HttpStatus.OK);    }

    private ResponseEntity<?> fallbackDeleteGamesBy(@PathVariable int playerId, RuntimeException e) {
        return new ResponseEntity<>(ResponseMessage.builder().responseCode(HttpStatus.OK.value())
                .message("There is a problem with the Game service. Try again").build(), HttpStatus.OK);    }
}
