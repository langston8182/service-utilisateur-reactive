package com.cmarchive.bank.serviceutilisateur.service;

import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationPermanenteDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service pour les operations permanentes utilisateurs.
 */
public interface OperationPermanenteService {

    Flux<OperationPermanenteDto> listerOperationPermanentesParUtilisateur(String utilisateurId);
    Mono<OperationPermanenteDto> ajouterOperationPermanenteAUtilisateur(
            String utilisateurId, OperationPermanenteDto operationPermanenteDto);
    Mono<OperationPermanenteDto> modifierOperationPermanenteUtilisateur(OperationPermanenteDto operationPermanenteDto);
    Mono<Void> supprimerOperationPermanente(String id);

}
