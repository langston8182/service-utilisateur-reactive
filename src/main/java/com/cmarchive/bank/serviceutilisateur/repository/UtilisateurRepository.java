package com.cmarchive.bank.serviceutilisateur.repository;

import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface CRUD pour manipuler des utilisateurs.
 */
@Repository
public interface UtilisateurRepository extends ReactiveMongoRepository<Utilisateur, String> {

}
