package com.cmarchive.bank.serviceutilisateur.repository;

import com.cmarchive.bank.serviceutilisateur.modele.Operation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface CRUD pour manipuler des operations.
 */
@Repository
public interface OperationRepository extends ReactiveMongoRepository<Operation, String> {

    Flux<Operation> findAllByUtilisateur_IdOrderByDateOperationDesc(String id);
    Mono<Operation> findByUtilisateur_IdAndId(String idUtilisateur, String idOperation);

}
