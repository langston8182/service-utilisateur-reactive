package com.cmarchive.bank.serviceutilisateur.modele;

import java.util.List;

public class Utilisateurs {

    private List<Utilisateur> utilisateurs;

    public Utilisateurs setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
        return this;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }
}
