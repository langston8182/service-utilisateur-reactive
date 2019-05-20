package com.cmarchive.bank.serviceutilisateur.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Opération permanentes liées a un utilisateur. Ces opérations se repetent chaque mois en general
 * au meme prix et a la meme date. Il s'agit principalement de factures ou les salaires.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Document
public class OperationPermanente {

    @Id
    private String id;
    private String intitule;
    private Integer jour;
    private BigDecimal prix;
    private Utilisateur utilisateur;
}
