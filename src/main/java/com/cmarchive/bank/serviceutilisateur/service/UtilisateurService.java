package com.cmarchive.bank.serviceutilisateur.service;

import com.cmarchive.bank.ressource.model.UtilisateurDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service CRUD utilisateur.
 */
public interface UtilisateurService {

    Flux<UtilisateurDto> listerUtilisateurs();
    Mono<UtilisateurDto> recupererUtilisateur(String id);
    Mono<UtilisateurDto> recupererUtilisateurParEmail(String email);
    Mono<UtilisateurDto> creerUtilisateur(UtilisateurDto utilisateurDto);
    Mono<UtilisateurDto> modifierUtilisateur(UtilisateurDto utilisateurDto);
    Mono<Void> supprimerUtilisateur(String id);
}
