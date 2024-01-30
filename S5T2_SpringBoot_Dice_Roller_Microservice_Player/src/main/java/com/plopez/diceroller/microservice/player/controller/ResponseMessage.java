package com.plopez.diceroller.microservice.player.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessage {
    private int responseCode;
    private String message;
    private String messageDescription;
    private Date responseTimeStamp;
}
