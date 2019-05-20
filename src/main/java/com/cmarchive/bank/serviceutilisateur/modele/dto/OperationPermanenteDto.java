package com.cmarchive.bank.serviceutilisateur.modele.dto;

import java.math.BigDecimal;

public class OperationPermanenteDto {

    private String id;
    private String intitule;
    private Integer jour;
    private BigDecimal prix;
    private UtilisateurDto utilisateurDto;

    public String getId() {
        return id;
    }

    public OperationPermanenteDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getIntitule() {
        return intitule;
    }

    public OperationPermanenteDto setIntitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public Integer getJour() {
        return jour;
    }

    public OperationPermanenteDto setJour(Integer jour) {
        this.jour = jour;
        return this;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public OperationPermanenteDto setPrix(BigDecimal prix) {
        this.prix = prix;
        return this;
    }

    public UtilisateurDto getUtilisateurDto() {
        return utilisateurDto;
    }

    public OperationPermanenteDto setUtilisateurDto(UtilisateurDto utilisateurDto) {
        this.utilisateurDto = utilisateurDto;
        return this;
    }
}
