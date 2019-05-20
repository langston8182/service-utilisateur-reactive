package com.cmarchive.bank.serviceutilisateur.exception;

public class OperationPermanenteNonTrouveeException extends RuntimeException {

    private String message;

    public OperationPermanenteNonTrouveeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
