package com.cmarchive.bank.serviceutilisateur.repository;

import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringJUnit4ClassRunner.class)
@DataMongoTest
public class UtilisateurRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void listerUtilisateur() {
        Utilisateur cyril = new Utilisateur()
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Marchive")
                .setPrenom("Cyril");
        Utilisateur melanie = new Utilisateur()
                .setEmail("melanie.boussat@gmail.com")
                .setNom("Boussat")
                .setPrenom("Melanie");
        mongoTemplate.save(cyril);
        mongoTemplate.save(melanie);

        Flux<Utilisateur> resultats = utilisateurRepository.findAll();

        StepVerifier.create(resultats.log())
                .expectSubscription()
                .expectNextMatches(utilisateur -> utilisateur.getEmail().equals("cyril.marchive@gmail.com"))
                .expectNextMatches(utilisateur -> utilisateur.getEmail().equals("melanie.boussat@gmail.com"))
                .verifyComplete();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void sauvegarderUtilisateur() {
        Utilisateur cyril = creerUtilisateur();

        Mono<Utilisateur> resultat = utilisateurRepository.save(cyril);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectNext(cyril)
                .verifyComplete();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void sauvegarderUtilisateur_UtilisateurDejaPresent() {
        Utilisateur cyril = creerUtilisateur();
        Utilisateur melanie = creerUtilisateur();
        mongoTemplate.save(cyril);

        Mono<Utilisateur> resultat = utilisateurRepository.save(melanie);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectError(DuplicateKeyException.class)
                .verify();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void modifierUtilisateur() {
        Utilisateur test = new Utilisateur()
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Test")
                .setPrenom("Test");
        mongoTemplate.save(test);
        Utilisateur cyril = creerUtilisateur()
                .setId(test.getId());

        Mono<Utilisateur> resultat = utilisateurRepository.save(cyril);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectNext(cyril)
                .verifyComplete();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void recupererUtilisateur() {
        Utilisateur cyril = creerUtilisateur();
        mongoTemplate.save(cyril);

        Mono<Utilisateur> resultat = utilisateurRepository.findById(cyril.getId());

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectNextMatches(utilisateur -> utilisateur.getEmail().equals("cyril.marchive@gmail.com"))
                .verifyComplete();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void supprimerUtilisateur() {
        Utilisateur cyril = creerUtilisateur();
        mongoTemplate.save(cyril);

        Mono<Void> resultat = utilisateurRepository.deleteById(cyril.getId());

        StepVerifier.create(resultat)
                .expectSubscription()
                .verifyComplete();
    }

    private Utilisateur creerUtilisateur() {
        return new Utilisateur()
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Marchive")
                .setPrenom("Cyril");
    }
}