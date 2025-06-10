package com.saraylg.matchmaker.matchmaker.exceptions;

import com.saraylg.matchmaker.matchmaker.dto.output.GenericResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidJamOperationException.class)
    public ResponseEntity<GenericResponseDTO<Object>> handleInvalidJamOperation(InvalidJamOperationException ex) {

        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                ex.getMessage(),
                String.valueOf(HttpStatus.FORBIDDEN.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PlayerAlreadyJoinedException.class)
    public ResponseEntity<GenericResponseDTO<Object>> handlePlayerAlreadyJoined(PlayerAlreadyJoinedException ex) {
        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                ex.getMessage(),
                String.valueOf(HttpStatus.CONFLICT.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JamNotFoundException.class)
    public ResponseEntity<GenericResponseDTO<Object>> handleJamNotFound(JamNotFoundException ex) {
        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                ex.getMessage(),
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(PlayerNotFoundInJamException.class)
    public ResponseEntity<GenericResponseDTO<Object>> handlePlayerNotFound(PlayerNotFoundInJamException ex) {

        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                ex.getMessage(),
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GenericResponseDTO<Object>> handleGeneric(RuntimeException ex) {
        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                ex.getMessage(),
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvitationNotFoundException.class)
    public ResponseEntity<GenericResponseDTO<Object>> handleInvitationNotFound(InvitationNotFoundException ex) {
        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                ex.getMessage(),
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GenericResponseDTO<Object>> handleUserNotFound(UserNotFoundException ex) {
        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                ex.getMessage(),
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JamAlreadyFullException.class)
    public ResponseEntity<GenericResponseDTO<Object>> handleJamAlreadyFull(JamAlreadyFullException ex) {
        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                ex.getMessage(),
                String.valueOf(HttpStatus.CONFLICT.value()),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
