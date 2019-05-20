package com.cmarchive.bank.serviceutilisateur.modele.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OperationDto {

    private String id;
    private String intitule;
    private LocalDate dateOperation;
    private BigDecimal prix;
    private UtilisateurDto utilisateurDto;
}
