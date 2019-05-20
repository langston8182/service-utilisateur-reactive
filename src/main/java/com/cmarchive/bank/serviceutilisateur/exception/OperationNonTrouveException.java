package com.cmarchive.bank.serviceutilisateur.exception;

public class OperationNonTrouveException extends RuntimeException {

    private String message;

    public OperationNonTrouveException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
