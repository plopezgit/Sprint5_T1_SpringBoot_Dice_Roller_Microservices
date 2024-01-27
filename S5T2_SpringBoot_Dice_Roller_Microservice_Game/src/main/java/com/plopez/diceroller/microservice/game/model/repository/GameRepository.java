package com.plopez.diceroller.microservice.game.model.repository;

import com.plopez.diceroller.microservice.game.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    List<Game> findGamesByPlayerId(int playerId);
    void deleteGamesByPlayerId(int playerId);
}
