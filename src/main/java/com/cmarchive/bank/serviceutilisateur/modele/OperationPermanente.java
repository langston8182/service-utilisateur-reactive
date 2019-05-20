package com.cmarchive.bank.serviceutilisateur.modele;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Opération permanentes liées a un utilisateur. Ces opérations se repetent chaque mois en general
 * au meme prix et a la meme date. Il s'agit principalement de factures ou les salaires.
 */
@Document
public class OperationPermanente {

    @Id
    private String id;
    private String intitule;
    private Integer jour;
    private BigDecimal prix;
    private Utilisateur utilisateur;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public OperationPermanente setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        return this;
    }

    public String getId() {
        return id;
    }

    public OperationPermanente setId(String id) {
        this.id = id;
        return this;
    }

    public String getIntitule() {
        return intitule;
    }

    public OperationPermanente setIntitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public Integer getJour() {
        return jour;
    }

    public OperationPermanente setJour(Integer jour) {
        this.jour = jour;
        return this;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public OperationPermanente setPrix(BigDecimal prix) {
        this.prix = prix;
        return this;
    }
}
