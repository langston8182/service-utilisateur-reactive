package com.cmarchive.bank.serviceutilisateur.repository;

import com.cmarchive.bank.serviceutilisateur.modele.Operation;
import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DataMongoTest
public class OperationRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private OperationRepository operationRepository;

    @Test
    public void sauvegarderOperationAvecUtilisateur() {
        Utilisateur cyril = creerUtilisateur();
        Operation operation = creerOperation(cyril);
        mongoTemplate.save(cyril);

        Mono<Operation> resultat = operationRepository.save(operation);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectNext(operation)
                .verifyComplete();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void supprimerOperation() {
        Utilisateur cyril = creerUtilisateur();
        Operation operation = creerOperation(cyril);
        mongoTemplate.save(cyril);
        mongoTemplate.save(operation);

        Mono<Void> resultat = operationRepository.deleteById(operation.getId());

        StepVerifier.create(resultat)
                .expectSubscription()
                .verifyComplete();
        Operation resultatOperation = mongoTemplate.findById(operation.getId(), Operation.class);
        assertThat(resultatOperation).isNull();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void listerOperationsParUtilisateur() {
        Utilisateur cyril = creerUtilisateur();
        Utilisateur utilisateurSauvegarde = mongoTemplate.save(cyril);
        Operation operation = creerOperation(utilisateurSauvegarde);
        mongoTemplate.insert(operation);

        Flux<Operation> resultat = operationRepository.findAllByUtilisateur_IdOrderByDateOperationDesc(cyril.getId());

        StepVerifier.create(resultat.log())
                .expectSubscription()
                .expectNextMatches(o -> o.getIntitule().equals("operation"))
                .verifyComplete();
    }

    private Operation creerOperation(Utilisateur cyril) {
        return new Operation()
                .setDateOperation(LocalDate.now())
                .setIntitule("operation")
                .setPrix(BigDecimal.TEN)
                .setUtilisateur(cyril);
    }

    private Utilisateur creerUtilisateur() {
        return new Utilisateur()
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Marchive")
                .setPrenom("Cyril");
    }
}