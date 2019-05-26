package com.cmarchive.bank.serviceutilisateur.modele.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDto {

    private String id;
    private String nom;
    private String prenom;
    private String email;
}
