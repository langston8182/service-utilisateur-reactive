package com.cmarchive.bank.serviceutilisateur.service;

import com.cmarchive.bank.serviceutilisateur.exception.OperationPermanenteNonTrouveeException;
import com.cmarchive.bank.serviceutilisateur.exception.UtilisateurNonTrouveException;
import com.cmarchive.bank.serviceutilisateur.mapper.OperationPermanenteMapper;
import com.cmarchive.bank.serviceutilisateur.mapper.UtilisateurMapper;
import com.cmarchive.bank.serviceutilisateur.modele.OperationPermanente;
import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationPermanenteDto;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateurDto;
import com.cmarchive.bank.serviceutilisateur.repository.OperationPermanenteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class OperationPermanenteServiceImplTest {

    @InjectMocks
    private OperationPermanenteServiceImpl operationPermanenteService;

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private OperationPermanenteRepository operationPermanenteRepository;

    @Mock
    private OperationPermanenteMapper operationPermanenteMapper;

    @Mock
    private UtilisateurMapper utilisateurMapper;

    @Test
    public void listerOperationPermanentesParUtilisateur() {
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        OperationPermanente operationPermanente1 = new OperationPermanente();
        OperationPermanente operationPermanente2 = new OperationPermanente();
        OperationPermanenteDto operationPermanenteDto1 = new OperationPermanenteDto();
        OperationPermanenteDto operationPermanenteDto2 = new OperationPermanenteDto();
        String id = "1";
        given(utilisateurService.recupererUtilisateur(id)).willReturn(Mono.just(utilisateurDto));
        given(operationPermanenteRepository
                .findAllByUtilisateur_Id(id))
                .willReturn(Flux.just(operationPermanente1, operationPermanente2));
        given(operationPermanenteMapper.mapVersOperationPermanenteDto(operationPermanente1)).willReturn(operationPermanenteDto1);
        given(operationPermanenteMapper.mapVersOperationPermanenteDto(operationPermanente2)).willReturn(operationPermanenteDto2);

        Flux<OperationPermanenteDto> resultat = operationPermanenteService.listerOperationPermanentesParUtilisateur(id);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectNext(operationPermanenteDto1, operationPermanenteDto2)
                .verifyComplete();
        then(operationPermanenteRepository).should().findAllByUtilisateur_Id(id);
        then(utilisateurService).should().recupererUtilisateur(id);
    }

    @Test
    public void listerOperationPermanentesParUtilisateur_UtilisateurNonExistant() {
        String id = "1";
        given(utilisateurService.recupererUtilisateur(id)).willReturn(Mono.error(new UtilisateurNonTrouveException("")));

        Flux<OperationPermanenteDto> resultat = operationPermanenteService.listerOperationPermanentesParUtilisateur(id);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectError(UtilisateurNonTrouveException.class)
                .verify();
    }

    @Test
    public void ajouterOperationPermanenteAUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        OperationPermanente operationPermanente = new OperationPermanente();
        OperationPermanenteDto operationPermanenteDto = new OperationPermanenteDto();
        OperationPermanente reponse = new OperationPermanente()
                .setUtilisateur(utilisateur);
        given(utilisateurService.recupererUtilisateur("1")).willReturn(Mono.just(utilisateurDto));
        given(utilisateurMapper.mapVersUtilisateur(utilisateurDto)).willReturn(utilisateur);
        given(operationPermanenteRepository.save(operationPermanente)).willReturn(Mono.just(reponse));
        given(operationPermanenteMapper.mapVersOperationPermanenteDto(reponse)).willReturn(operationPermanenteDto);
        given(operationPermanenteMapper.mapVersOperationPermanente(operationPermanenteDto))
                .willReturn(operationPermanente);

        Mono<OperationPermanenteDto> resultat = operationPermanenteService.ajouterOperationPermanenteAUtilisateur(
                "1", operationPermanenteDto);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectNext(operationPermanenteDto)
                .verifyComplete();
        then(operationPermanenteRepository).should().save(operationPermanente);
    }

    @Test
    public void ajouterOperationPermanenteAUtilisateur_UtilisateurNonExistant() {
        OperationPermanenteDto operationPermanenteDto = new OperationPermanenteDto();
        given(utilisateurService.recupererUtilisateur("1")).willReturn(Mono.error(new UtilisateurNonTrouveException("")));

        Mono<OperationPermanenteDto> resultat = operationPermanenteService.ajouterOperationPermanenteAUtilisateur(
                "1", operationPermanenteDto);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectError(UtilisateurNonTrouveException.class)
                .verify();
    }

    @Test
    public void modifierOperationPermanenteUtilisateur() {
        String id = "1";
        OperationPermanenteDto operationPermanenteDto = new OperationPermanenteDto().setId(id);
        String email = "email";
        OperationPermanente operationPermanenteBdd = new OperationPermanente()
                .setUtilisateur(new Utilisateur().setEmail(email));
        OperationPermanente operationPermanente = new OperationPermanente();
        OperationPermanente operationPermanenteReponse = new OperationPermanente()
                .setUtilisateur(new Utilisateur().setEmail(email));
        OperationPermanenteDto operationPermanenteDtoReponse = new OperationPermanenteDto()
                .setUtilisateurDto(new UtilisateurDto().setEmail(email));
        given(operationPermanenteRepository.findById(id)).willReturn(Mono.just(operationPermanenteBdd));
        given(operationPermanenteMapper.mapVersOperationPermanente(operationPermanenteDto))
                .willReturn(operationPermanente);
        given(operationPermanenteRepository.save(operationPermanente)).willReturn(Mono.just(operationPermanenteReponse));
        given(operationPermanenteMapper.mapVersOperationPermanenteDto(operationPermanenteReponse))
                .willReturn(operationPermanenteDtoReponse);

        Mono<OperationPermanenteDto> resultat = operationPermanenteService.modifierOperationPermanenteUtilisateur(
                operationPermanenteDto);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectNextMatches(opDto -> opDto.getUtilisateurDto().getEmail().equals(email))
                .verifyComplete();
        then(operationPermanenteRepository).should().findById(id);
        then(operationPermanenteRepository).should().save(operationPermanente);
    }

    @Test
    public void modifierOperationPermanenteUtilisateur_OperationPermanenteNonTrouvee() {
        String id = "1";
        OperationPermanenteDto operationPermanenteDto = new OperationPermanenteDto().setId(id);
        given(operationPermanenteRepository.findById(id)).willReturn(Mono.error(new OperationPermanenteNonTrouveeException("")));

        Mono<OperationPermanenteDto> resultat = operationPermanenteService.modifierOperationPermanenteUtilisateur(
                operationPermanenteDto);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectError(OperationPermanenteNonTrouveeException.class)
                .verify();
    }

    @Test
    public void supprimerOperationPermanente() {
        String id = "1";
        given(operationPermanenteRepository.deleteById(id)).willReturn(Mono.empty());

        Mono<Void> resultat = operationPermanenteService.supprimerOperationPermanente(id);

        StepVerifier.create(resultat)
                .expectSubscription()
                .verifyComplete();
        then(operationPermanenteRepository).should().deleteById(id);
    }
}