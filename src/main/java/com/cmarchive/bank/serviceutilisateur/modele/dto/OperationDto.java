package com.cmarchive.bank.serviceutilisateur.modele.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OperationDto {

    private String id;
    private String intitule;
    private LocalDate dateOperation;
    private BigDecimal prix;
    private UtilisateurDto utilisateurDto;

    public String getId() {
        return id;
    }

    public OperationDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getIntitule() {
        return intitule;
    }

    public OperationDto setIntitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public LocalDate getDateOperation() {
        return dateOperation;
    }

    public OperationDto setDateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
        return this;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public OperationDto setPrix(BigDecimal prix) {
        this.prix = prix;
        return this;
    }

    public UtilisateurDto getUtilisateurDto() {
        return utilisateurDto;
    }

    public OperationDto setUtilisateurDto(UtilisateurDto utilisateurDto) {
        this.utilisateurDto = utilisateurDto;
        return this;
    }
}
