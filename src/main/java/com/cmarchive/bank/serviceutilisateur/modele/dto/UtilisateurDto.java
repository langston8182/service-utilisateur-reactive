package com.cmarchive.bank.serviceutilisateur.modele.dto;

public class UtilisateurDto {

    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public UtilisateurDto setId(String id) {
        this.id = id;
        return this;
    }

    public UtilisateurDto setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public UtilisateurDto setPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public UtilisateurDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public UtilisateurDto setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
        return this;
    }
}
