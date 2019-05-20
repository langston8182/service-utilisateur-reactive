package com.cmarchive.bank.serviceutilisateur.exception;

public class UtilisateurNonTrouveException extends RuntimeException {

    private String message;

    public UtilisateurNonTrouveException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
