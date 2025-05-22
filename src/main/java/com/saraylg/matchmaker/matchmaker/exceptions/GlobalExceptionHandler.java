package com.saraylg.matchmaker.matchmaker.exceptions;

import com.saraylg.matchmaker.matchmaker.exceptions.InvalidJamOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidJamOperationException.class)
    public ResponseEntity<String> handleInvalidJamOperation(InvalidJamOperationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(PlayerAlreadyJoinedException.class)
    public ResponseEntity<String> handlePlayerAlreadyJoined(PlayerAlreadyJoinedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(JamNotFoundException.class)
    public ResponseEntity<String> handleJamNotFound(JamNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PlayerNotFoundInJamException.class)
    public ResponseEntity<String> handlePlayerNotFound(PlayerNotFoundInJamException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGeneric(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
