package com.cmarchive.bank.serviceutilisateur.modele;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Operation bancaire. Peut etre soit un credit(salaire, ...) ou un debit(factures, ...).
 */
@Document
public class Operation {

    @Id
    private String id;
    private String intitule;
    private LocalDate dateOperation;
    private BigDecimal prix;
    private Utilisateur utilisateur;

    public String getId() {
        return id;
    }

    public Operation setId(String id) {
        this.id = id;
        return this;
    }

    public String getIntitule() {
        return intitule;
    }

    public Operation setIntitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public LocalDate getDateOperation() {
        return dateOperation;
    }

    public Operation setDateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
        return this;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public Operation setPrix(BigDecimal prix) {
        this.prix = prix;
        return this;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Operation setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        return this;
    }
}
