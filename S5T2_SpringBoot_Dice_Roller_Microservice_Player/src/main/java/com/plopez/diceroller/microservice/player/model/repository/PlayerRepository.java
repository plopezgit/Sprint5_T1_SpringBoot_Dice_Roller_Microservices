package com.plopez.diceroller.microservice.player.model.repository;

import com.plopez.diceroller.microservice.player.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    // Todo delete unused and regression test
    Optional<Player> findByNickname(String nickname);
    boolean existsByNickname(String nickname);
    boolean existsById(int playerId);
}
