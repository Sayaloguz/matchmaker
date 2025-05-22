package com.saraylg.matchmaker.matchmaker.exceptions;

public class PlayerAlreadyJoinedException extends RuntimeException {
    public PlayerAlreadyJoinedException(String message) {
        super(message);
    }
}
