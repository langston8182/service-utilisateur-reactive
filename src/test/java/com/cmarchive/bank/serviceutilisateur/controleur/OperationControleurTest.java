package com.cmarchive.bank.serviceutilisateur.controleur;

import com.cmarchive.bank.serviceutilisateur.exception.UtilisateurNonTrouveException;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationDto;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateurDto;
import com.cmarchive.bank.serviceutilisateur.service.OperationService;
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
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = OperationControleur.class)
@WithMockUser
public class OperationControleurTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private OperationService operationService;

    @Test
    public void listerOperationUtilisateur() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationDto operationDto1 = creerOperationDto(utilisateurDto);
        OperationDto operationDto2 = creerOperationDto(utilisateurDto);
        given(operationService.listerOperationsParUtilisateur("1")).willReturn(Flux.just(operationDto1, operationDto2));

        webTestClient.get().uri("/operations/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(OperationDto.class)
                .hasSize(2);
    }

    @Test
    public void listerOperationUtilisateur_UtilisateurNonExistant() {
        given(operationService.listerOperationsParUtilisateur("1")).willReturn(Flux.error(new UtilisateurNonTrouveException("")));

        webTestClient.get().uri("/operations/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void ajouterOperationAUtilisateur() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationDto operationDto = creerOperationDto(utilisateurDto);
        OperationDto reponse = new OperationDto()
                .setIntitule("test")
                .setUtilisateurDto(utilisateurDto);
        given(operationService.ajouterOperationAUtilisateur(anyString(), any(OperationDto.class)))
                .willReturn(Mono.just(reponse));

        webTestClient.mutateWith(csrf())
                .post().uri("/operations/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(operationDto), OperationDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.intitule").isEqualTo("test")
                .jsonPath("$.utilisateurDto.prenom").isEqualTo("Cyril");
    }

    @Test
    public void ajouterOperationAUtilisateur_UtilisateurNonExistant() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationDto operationDto = creerOperationDto(utilisateurDto);
        given(operationService.ajouterOperationAUtilisateur(anyString(), any(OperationDto.class)))
                .willReturn(Mono.error(new UtilisateurNonTrouveException("")));

        webTestClient.mutateWith(csrf())
                .post().uri("/operations/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(operationDto), OperationDto.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void modifierOperationUtilisateur() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationDto operationDto = creerOperationDto(utilisateurDto);
        OperationDto reponse = new OperationDto()
                .setIntitule("test")
                .setUtilisateurDto(utilisateurDto);
        given(operationService.modifierOperationUtilisateur(any(OperationDto.class)))
                .willReturn(Mono.just(reponse));

        webTestClient.mutateWith(csrf())
                .put().uri("/operations")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(operationDto), OperationDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.intitule").isEqualTo("test")
                .jsonPath("$.utilisateurDto.prenom").isEqualTo("Cyril");
    }

    @Test
    public void modifierOperationUtilisateur_OperationNonTrouvee() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationDto operationDto = creerOperationDto(utilisateurDto);
        given(operationService.modifierOperationUtilisateur(any(OperationDto.class)))
                .willReturn(Mono.error(new UtilisateurNonTrouveException("")));

        webTestClient.mutateWith(csrf())
                .put().uri("/operations")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(operationDto), OperationDto.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void supprimerOperationUtilisateur() {
        given(operationService.supprimerOperation("1")).willReturn(Mono.empty());

        webTestClient.mutateWith(csrf())
                .delete().uri("/operations/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);
    }

    private OperationDto creerOperationDto(UtilisateurDto cyril) {
        return new OperationDto()
                .setDateOperation(LocalDate.now())
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