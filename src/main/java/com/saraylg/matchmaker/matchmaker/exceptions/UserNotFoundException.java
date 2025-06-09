package com.saraylg.matchmaker.matchmaker.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String steamId) {
        super("Usuario con SteamID " + steamId + " no encontrado.");
    }
}
