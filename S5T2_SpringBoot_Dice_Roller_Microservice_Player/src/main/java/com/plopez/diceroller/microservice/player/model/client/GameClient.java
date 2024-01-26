package com.plopez.diceroller.microservice.player.model.client;

import com.plopez.diceroller.microservice.player.model.dto.GameDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name= "game-service", url="http://localhost:8081/games")
public interface GameClient {

    @PostMapping
    GameDTO createGame(@RequestBody GameDTO gameDTO);

    @GetMapping("/player/{playerId}")
    List<GameDTO> getGames(@RequestBody int playerId);
}
