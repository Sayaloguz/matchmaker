package com.saraylg.matchmaker.matchmaker.exceptions;

import com.saraylg.matchmaker.matchmaker.dto.output.GenericResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidJamOperationException.class)
    public ResponseEntity<GenericResponseDTO<Object>>handleInvalidJamOperation(InvalidJamOperationException ex) {

        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                "Operación de la jam no permitida",
                String.valueOf(HttpStatus.FORBIDDEN.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PlayerAlreadyJoinedException.class)
    public ResponseEntity<GenericResponseDTO<Object>> handlePlayerAlreadyJoined(PlayerAlreadyJoinedException ex) {
        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                "El/la jugador/a se encuentra ya en la jam",
                String.valueOf(HttpStatus.CONFLICT.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JamNotFoundException.class)
    public ResponseEntity<GenericResponseDTO<Object>> handleJamNotFound(JamNotFoundException ex) {
        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                "Jam no encontrada",
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(PlayerNotFoundInJamException.class)
    public ResponseEntity<GenericResponseDTO<Object>> handlePlayerNotFound(PlayerNotFoundInJamException ex) {

        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                "Jugador/a no encontrado/a en la jam",
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GenericResponseDTO<Object>> handleGeneric(RuntimeException ex) {
        GenericResponseDTO<Object> response = new GenericResponseDTO<>(
                "Fallo en la petición",
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

}
