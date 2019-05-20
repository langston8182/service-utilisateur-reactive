package com.cmarchive.bank.serviceutilisateur.modele.dto;

import java.util.List;

public class OperationPermanentesDto {

    List<OperationPermanenteDto> operationPermanenteDtos;

    public List<OperationPermanenteDto> getOperationPermanenteDtos() {
        return operationPermanenteDtos;
    }

    public OperationPermanentesDto setOperationPermanenteDtos(List<OperationPermanenteDto> operationPermanenteDtos) {
        this.operationPermanenteDtos = operationPermanenteDtos;
        return this;
    }
}
