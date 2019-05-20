package com.cmarchive.bank.serviceutilisateur.controleur;

import com.cmarchive.bank.serviceutilisateur.exception.UtilisateurDejaPresentException;
import com.cmarchive.bank.serviceutilisateur.exception.UtilisateurNonTrouveException;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateurDto;
import com.cmarchive.bank.serviceutilisateur.service.UtilisateurService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = UtilisateurControleur.class)
@WithMockUser
public class UtilisateurControleurTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UtilisateurService utilisateurService;

    @Test
    public void listerUtilisateurs() {
        UtilisateurDto cyril = new UtilisateurDto()
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Marchive")
                .setPrenom("Cyril");
        UtilisateurDto melanie = new UtilisateurDto()
                .setEmail("melanie.boussat@gmail.com")
                .setNom("Boussat")
                .setPrenom("Melanie");
        given(utilisateurService.listerUtilisateurs()).willReturn(Flux.just(cyril, melanie));

        webTestClient.get().uri("/utilisateurs")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UtilisateurDto.class)
                .hasSize(2);
    }

    @Test
    public void recupererUtilisateur() {
        UtilisateurDto utilisateur = creerUtilisateurDto();
        given(utilisateurService.recupererUtilisateur("1")).willReturn(Mono.just(utilisateur));

        webTestClient.get().uri("/utilisateurs/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nom").isEqualTo("Marchive")
                .jsonPath("$.prenom").isEqualTo("Cyril")
                .jsonPath("$.email").isEqualTo("cyril.marchive@gmail.com");
    }

    @Test
    public void recupererUtilisateur_UtilisateurNonTrouve() {
        given(utilisateurService.recupererUtilisateur("1")).willReturn(Mono.error(new UtilisateurNonTrouveException("")));

        webTestClient.get().uri("/utilisateurs/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void supprimerUtilisateur() {
        given(utilisateurService.supprimerUtilisateur("1")).willReturn(Mono.empty());

        webTestClient
                .mutateWith(csrf())
                .delete().uri("/utilisateurs/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);
    }

    @Test
    public void supprimerUtilisateur_UtilisateurNonExistant() {
        given(utilisateurService.supprimerUtilisateur("1")).willReturn(Mono.error(new UtilisateurNonTrouveException("")));

        webTestClient
                .mutateWith(csrf())
                .delete().uri("/utilisateurs/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void sauvegarderUtilisateur() {
        UtilisateurDto cyril = creerUtilisateurDto();
        UtilisateurDto reponse = new UtilisateurDto()
                .setId("1");
        given(utilisateurService.creerUtilisateur(any(UtilisateurDto.class))).willReturn(Mono.just(reponse));

        webTestClient
                .mutateWith(csrf())
                .post().uri("/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(cyril), UtilisateurDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty();
    }

    @Test
    public void sauvegarderUtilisateur_UtilisateurDejaPresent() {
        UtilisateurDto cyril = creerUtilisateurDto();
        given(utilisateurService.creerUtilisateur(any(UtilisateurDto.class)))
                .willReturn(Mono.error(new UtilisateurDejaPresentException("")));

        webTestClient
                .mutateWith(csrf())
                .post().uri("/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(cyril), UtilisateurDto.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void modifierUtilisateur() {
        UtilisateurDto cyril = creerUtilisateurDto();
        UtilisateurDto reponse = creerUtilisateurDto()
                .setNom("Boussat");
        given(utilisateurService.modifierUtilisateur(any(UtilisateurDto.class))).willReturn(Mono.just(reponse));

        webTestClient
                .mutateWith(csrf())
                .put().uri("/utilisateurs")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(cyril), UtilisateurDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nom").isEqualTo("Boussat");
    }

    @Test
    public void modifierUtilisateur_UtilisateurNonExistant() {
        UtilisateurDto cyril = new UtilisateurDto();
        given(utilisateurService.modifierUtilisateur(any(UtilisateurDto.class))).willReturn(Mono.error(new UtilisateurNonTrouveException("")));

        webTestClient
                .mutateWith(csrf())
                .put().uri("/utilisateurs")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(cyril), UtilisateurDto.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    private UtilisateurDto creerUtilisateurDto() {
        return new UtilisateurDto()
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Marchive")
                .setPrenom("Cyril");
    }
}