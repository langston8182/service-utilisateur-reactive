package com.cmarchive.bank.serviceutilisateur.controleur;

import com.cmarchive.bank.serviceutilisateur.exception.OperationPermanenteNonTrouveeException;
import com.cmarchive.bank.serviceutilisateur.exception.UtilisateurNonTrouveException;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationPermanenteDto;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateurDto;
import com.cmarchive.bank.serviceutilisateur.service.OperationPermanenteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = OperationPermanenteControleur.class)
@WithMockUser
public class OperationPermanenteControleurTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private OperationPermanenteService operationPermanenteService;

    @Test
    public void listerOperationPermanenteUtilisateur() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationPermanenteDto operationPermanenteDto1 = creerOperationPermanenteDto(utilisateurDto);
        OperationPermanenteDto operationPermanenteDto2 = creerOperationPermanenteDto(utilisateurDto);
        given(operationPermanenteService.listerOperationPermanentesParUtilisateur("1"))
                .willReturn(Flux.just(operationPermanenteDto1, operationPermanenteDto2));

        webTestClient.get().uri("/operations-permanentes/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(OperationPermanenteDto.class)
                .hasSize(2);
    }

    @Test
    public void listerOperationPermanenteUtilisateur_UtilisateurNonExistant() {
        given(operationPermanenteService.listerOperationPermanentesParUtilisateur("1"))
                .willReturn(Flux.error(new UtilisateurNonTrouveException("")));

        webTestClient.get().uri("/operations-permanentes/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void ajouterOperationPermanenteAUtilisateur() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationPermanenteDto operationPermanenteDto = creerOperationPermanenteDto(utilisateurDto);
        OperationPermanenteDto reponse = new OperationPermanenteDto()
                .setIntitule("test")
                .setUtilisateurDto(utilisateurDto);
        given(operationPermanenteService.ajouterOperationPermanenteAUtilisateur(
                anyString(), any(OperationPermanenteDto.class)))
                .willReturn(Mono.just(reponse));

        webTestClient.mutateWith(csrf())
                .post().uri("/operations-permanentes/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(operationPermanenteDto), OperationPermanenteDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.intitule").isEqualTo("test")
                .jsonPath("$.utilisateurDto.prenom").isEqualTo("Cyril");
    }

    @Test
    public void ajouterOperationPermanenteAUtilisateur_UtilisateurNonExistant() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationPermanenteDto operationPermanenteDto = creerOperationPermanenteDto(utilisateurDto);
        given(operationPermanenteService.ajouterOperationPermanenteAUtilisateur(
                anyString(), any(OperationPermanenteDto.class)))
                .willReturn(Mono.error(new UtilisateurNonTrouveException("")));

        webTestClient.mutateWith(csrf())
                .post().uri("/operations-permanentes/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(operationPermanenteDto), OperationPermanenteDto.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void modifierOperationPermanenteUtilisateur() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationPermanenteDto operationPermanenteDto = creerOperationPermanenteDto(utilisateurDto);
        OperationPermanenteDto reponse = new OperationPermanenteDto()
                .setIntitule("test")
                .setUtilisateurDto(utilisateurDto);
        given(operationPermanenteService.modifierOperationPermanenteUtilisateur(any(OperationPermanenteDto.class)))
                .willReturn(Mono.just(reponse));

        webTestClient.mutateWith(csrf())
                .put().uri("/operations-permanentes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(operationPermanenteDto), OperationPermanenteDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.intitule").isEqualTo("test")
                .jsonPath("$.utilisateurDto.prenom").isEqualTo("Cyril");
    }

    @Test
    public void modifierOperationPermanenteUtilisateur_OperationPermanenteNonTrouvee() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationPermanenteDto operationPermanenteDto = creerOperationPermanenteDto(utilisateurDto);
        given(operationPermanenteService.modifierOperationPermanenteUtilisateur(any(OperationPermanenteDto.class)))
                .willReturn(Mono.error(new OperationPermanenteNonTrouveeException("")));

        webTestClient.mutateWith(csrf())
                .put().uri("/operations-permanentes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(operationPermanenteDto), OperationPermanenteDto.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void supprimerOperationPermanenteUtilisateur() {
        given(operationPermanenteService.supprimerOperationPermanente("1")).willReturn(Mono.empty());

        webTestClient.mutateWith(csrf())
                .delete().uri("/operations-permanentes/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);
    }

    private OperationPermanenteDto creerOperationPermanenteDto(UtilisateurDto cyril) {
        return new OperationPermanenteDto()
                .setJour(12)
                .setIntitule("operation")
                .setPrix(BigDecimal.TEN)
                .setUtilisateurDto(cyril);
    }

    private UtilisateurDto creerUtilisateurDto() {
        return new UtilisateurDto()
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Marchive")
                .setPrenom("Cyril");
    }
}