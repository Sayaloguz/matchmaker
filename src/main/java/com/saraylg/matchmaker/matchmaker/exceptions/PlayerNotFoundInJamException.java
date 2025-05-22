package com.saraylg.matchmaker.matchmaker.exceptions;

public class PlayerNotFoundInJamException extends RuntimeException {
    public PlayerNotFoundInJamException(String message) {
        super(message);
    }
}