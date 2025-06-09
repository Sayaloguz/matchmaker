package com.saraylg.matchmaker.matchmaker.exceptions;

public class JamAlreadyFullException extends RuntimeException {
    public JamAlreadyFullException(String jamId) {
        super("La jam con ID " + jamId + " ya está llena.");
    }
}
