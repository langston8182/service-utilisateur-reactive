package com.cmarchive.bank.serviceutilisateur.modele.dto;

import java.util.List;

public class UtilisateursDto {

    private List<UtilisateurDto> utilisateursDtos;

    public List<UtilisateurDto> getUtilisateursDtos() {
        return utilisateursDtos;
    }

    public UtilisateursDto setUtilisateursDtos(List<UtilisateurDto> utilisateursDtos) {
        this.utilisateursDtos = utilisateursDtos;
        return this;
    }
}
