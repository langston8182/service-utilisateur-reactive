package com.cmarchive.bank.serviceutilisateur.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Operation bancaire. Peut etre soit un credit(salaire, ...) ou un debit(factures, ...).
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Operation {

    @Id
    private String id;
    private String intitule;
    private LocalDate dateOperation;
    private BigDecimal prix;
    private Utilisateur utilisateur;
}
