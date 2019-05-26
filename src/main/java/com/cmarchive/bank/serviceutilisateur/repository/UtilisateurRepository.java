package com.cmarchive.bank.serviceutilisateur.repository;

import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Interface CRUD pour manipuler des utilisateurs.
 */
@Repository
public interface UtilisateurRepository extends ReactiveMongoRepository<Utilisateur, String> {

    Mono<Utilisateur> findByEmail(String email);

}
