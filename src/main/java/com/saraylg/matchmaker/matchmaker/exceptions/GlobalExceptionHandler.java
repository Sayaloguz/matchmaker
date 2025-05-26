package com.saraylg.matchmaker.matchmaker.exceptions;

import com.saraylg.matchmaker.matchmaker.model.GenericResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidJamOperationException.class)
    public ResponseEntity<GenericResponseEntity<Object>>handleInvalidJamOperation(InvalidJamOperationException ex) {

        GenericResponseEntity<Object> response = new GenericResponseEntity<>(
                "Operación de la jam no permitida",
                String.valueOf(HttpStatus.FORBIDDEN.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PlayerAlreadyJoinedException.class)
    public ResponseEntity<GenericResponseEntity<Object>> handlePlayerAlreadyJoined(PlayerAlreadyJoinedException ex) {
        GenericResponseEntity<Object> response = new GenericResponseEntity<>(
                "El/la jugador/a se encuentra ya en la jam",
                String.valueOf(HttpStatus.CONFLICT.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JamNotFoundException.class)
    public ResponseEntity<GenericResponseEntity<Object>> handleJamNotFound(JamNotFoundException ex) {
        GenericResponseEntity<Object> response = new GenericResponseEntity<>(
                "Jam no encontrada",
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(PlayerNotFoundInJamException.class)
    public ResponseEntity<GenericResponseEntity<Object>> handlePlayerNotFound(PlayerNotFoundInJamException ex) {

        GenericResponseEntity<Object> response = new GenericResponseEntity<>(
                "Jugador/a no encontrado/a en la jam",
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GenericResponseEntity<Object>> handleGeneric(RuntimeException ex) {
        GenericResponseEntity<Object> response = new GenericResponseEntity<>(
                "Fallo en la petición",
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
