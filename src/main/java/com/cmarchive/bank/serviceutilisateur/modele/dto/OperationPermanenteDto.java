package com.cmarchive.bank.serviceutilisateur.modele.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OperationPermanenteDto {

    private String identifiant;
    private String intitule;
    private Integer jour;
    private BigDecimal prix;
    private UtilisateurDto utilisateurDto;
}
