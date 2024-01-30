package com.plopez.diceroller.microservice.player.controller;

import com.plopez.diceroller.microservice.player.model.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.rmi.ServerException;
import java.util.Date;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseMessage> playerNotFoundExceptionHandler(PlayerNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .messageDescription(request.getDescription(false))
                .responseTimeStamp(new Date())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NickNameAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
    public ResponseEntity<ResponseMessage> nickNameAlreadyExistExceptionHandler(NickNameAlreadyExistException exception, WebRequest request) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.ALREADY_REPORTED.value())
                .message(exception.getMessage())
                .messageDescription(request.getDescription(false))
                .responseTimeStamp(new Date())
                .build(), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseMessage> HttpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException exception, WebRequest request) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .messageDescription(request.getDescription(false))
                .responseTimeStamp(new Date())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseMessage> serverExceptionHandler(Exception exception, WebRequest request) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .messageDescription(request.getDescription(false))
                .responseTimeStamp(new Date())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CreateGameServiceClientFallbackException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ResponseMessage> createGamesServiceClientFallbackExceptionHandler(CreateGameServiceClientFallbackException exception, WebRequest request) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message(exception.getMessage())
                .messageDescription(request.getDescription(false))
                .responseTimeStamp(new Date())
                .build(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(DeleteGamesServiceClientFallbackException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ResponseMessage> deleteGamesServiceClientFallbackExceptionHandler(DeleteGamesServiceClientFallbackException exception, WebRequest request) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message(exception.getMessage())
                .messageDescription(request.getDescription(false))
                .responseTimeStamp(new Date())
                .build(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(GetGamesServiceClientFallbackException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ResponseMessage> getGamesServiceClientFallbackExceptionHandler(GetGamesServiceClientFallbackException exception, WebRequest request) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .responseCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message(exception.getMessage())
                .messageDescription(request.getDescription(false))
                .responseTimeStamp(new Date())
                .build(), HttpStatus.SERVICE_UNAVAILABLE);
    }

}
