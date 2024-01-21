package com.plopez.diceroller.microservice.player.model.repository;

import com.plopez.diceroller.microservice.player.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
