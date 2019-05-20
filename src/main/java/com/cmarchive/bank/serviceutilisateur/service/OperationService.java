package com.cmarchive.bank.serviceutilisateur.service;

import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service pour les operations utilisateurs.
 */
public interface OperationService {

    Flux<OperationDto> listerOperationsParUtilisateur(String utilisateurId);
    Mono<OperationDto> ajouterOperationAUtilisateur(String utilisateurId, OperationDto operationDto);
    Mono<OperationDto> modifierOperationUtilisateur(OperationDto operationDto);
    Mono<Void> supprimerOperation(String id);

}
