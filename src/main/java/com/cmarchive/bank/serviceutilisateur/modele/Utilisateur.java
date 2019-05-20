package com.cmarchive.bank.serviceutilisateur.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Utilisateur de l'application bank;
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Utilisateur {

    @Id
    private String id;
    private String nom;
    private String prenom;

    @Indexed(unique = true)
    private String email;

    private String motDePasse;
}
