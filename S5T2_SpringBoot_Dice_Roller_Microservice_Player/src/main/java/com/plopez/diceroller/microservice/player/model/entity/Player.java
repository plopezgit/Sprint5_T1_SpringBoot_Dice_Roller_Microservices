package com.plopez.diceroller.microservice.player.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nickname;
    @CreationTimestamp
    private LocalDateTime registrationTimeStamp;
    private float gameSuccessRate;

    public Player(String nickname) {
        this.nickname = nickname;
    }

}
