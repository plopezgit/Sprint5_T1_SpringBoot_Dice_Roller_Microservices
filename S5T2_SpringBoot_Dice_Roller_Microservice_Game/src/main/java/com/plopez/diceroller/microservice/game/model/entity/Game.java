package com.plopez.diceroller.microservice.game.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="playerId")
    private int playerId;
    @Column(name="die1")
    private int die1;
    @Column(name="die2")
    private int die2;
    @Column(name="result")
    private int result;

}
