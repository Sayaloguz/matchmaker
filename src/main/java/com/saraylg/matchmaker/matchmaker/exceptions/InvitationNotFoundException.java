package com.saraylg.matchmaker.matchmaker.exceptions;

public class InvitationNotFoundException extends RuntimeException {
    public InvitationNotFoundException(String invitationId) {
        super("Invitación con ID " + invitationId + " no encontrada.");
    }
}
