package com.cmarchive.bank.serviceutilisateur.modele.dto;

import java.util.List;

public class OperationsDto {

    List<OperationDto> operationDtos;

    public List<OperationDto> getOperationDtos() {
        return operationDtos;
    }

    public OperationsDto setOperationDtos(List<OperationDto> operationDtos) {
        this.operationDtos = operationDtos;
        return this;
    }
}
