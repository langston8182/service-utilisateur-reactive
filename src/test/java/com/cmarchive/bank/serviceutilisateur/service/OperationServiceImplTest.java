package com.cmarchive.bank.serviceutilisateur.service;

import com.cmarchive.bank.serviceutilisateur.exception.OperationNonTrouveException;
import com.cmarchive.bank.serviceutilisateur.exception.UtilisateurNonTrouveException;
import com.cmarchive.bank.serviceutilisateur.mapper.OperationMapper;
import com.cmarchive.bank.serviceutilisateur.mapper.OperationsMapper;
import com.cmarchive.bank.serviceutilisateur.mapper.UtilisateurMapper;
import com.cmarchive.bank.serviceutilisateur.modele.Operation;
import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationDto;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateurDto;
import com.cmarchive.bank.serviceutilisateur.repository.OperationRepository;
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
public class OperationServiceImplTest {

    @InjectMocks
    private OperationServiceImpl operationService;

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private OperationMapper operationMapper;

    @Mock
    private UtilisateurMapper utilisateurMapper;

    @Test
    public void listerOperationsParUtilisateur() {
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        Operation operation1 = new Operation();
        Operation operation2 = new Operation();
        OperationDto operationDto1 = new OperationDto();
        OperationDto operationDto2 = new OperationDto();
        String id = "1";
        given(operationRepository
                .findAllByUtilisateur_IdOrderByDateOperationDesc(id))
                .willReturn(Flux.just(operation1, operation2));
        given(operationMapper.mapVersOperationDto(operation1)).willReturn(operationDto1);
        given(operationMapper.mapVersOperationDto(operation2)).willReturn(operationDto2);
        given(utilisateurService.recupererUtilisateur(id)).willReturn(Mono.just(utilisateurDto));

        Flux<OperationDto> resultat = operationService.listerOperationsParUtilisateur(id);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectNext(operationDto1, operationDto2)
                .verifyComplete();
        then(operationRepository).should().findAllByUtilisateur_IdOrderByDateOperationDesc(id);
        then(utilisateurService).should().recupererUtilisateur(id);
    }

    @Test
    public void listerOperationsParUtilisateur_UtilisateurNonExistant() {
        String id = "1";
        given(utilisateurService.recupererUtilisateur(id)).willReturn(Mono.error(new UtilisateurNonTrouveException("")));

        Flux<OperationDto> resultat = operationService.listerOperationsParUtilisateur(id);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectError(UtilisateurNonTrouveException.class)
                .verify();
    }

    @Test
    public void ajouterOperationAUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        Operation operation = new Operation();
        OperationDto operationDto = new OperationDto();
        Operation reponse = new Operation()
                .setUtilisateur(utilisateur);
        given(utilisateurService.recupererUtilisateur("1")).willReturn(Mono.just(utilisateurDto));
        given(utilisateurMapper.mapVersUtilisateur(utilisateurDto)).willReturn(utilisateur);
        given(operationRepository.save(operation)).willReturn(Mono.just(reponse));
        given(operationMapper.mapVersOperationDto(reponse)).willReturn(operationDto);
        given(operationMapper.mapVersOperation(operationDto)).willReturn(operation);

        Mono<OperationDto> resultat = operationService.ajouterOperationAUtilisateur("1", operationDto);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectNext(operationDto)
                .verifyComplete();
        then(operationRepository).should().save(operation);
    }

    @Test
    public void ajouterOperationAUtilisateur_UtilisateurNonExistant() {
        OperationDto operationDto = new OperationDto();
        given(utilisateurService.recupererUtilisateur("1")).willReturn(Mono.error(new UtilisateurNonTrouveException("")));

        Mono<OperationDto> resultat = operationService.ajouterOperationAUtilisateur("1", operationDto);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectError(UtilisateurNonTrouveException.class)
                .verify();
    }

    @Test
    public void modifierOperationUtilisateur() {
        String id = "1";
        OperationDto operationDto = new OperationDto().setId(id);
        String email = "email";
        Operation operationBdd = new Operation()
                .setUtilisateur(new Utilisateur().setEmail(email));
        Operation operation = new Operation();
        Operation operationReponse = new Operation()
                .setUtilisateur(new Utilisateur().setEmail(email));
        OperationDto operationDtoReponse = new OperationDto()
                .setUtilisateurDto(new UtilisateurDto().setEmail(email));
        given(operationRepository.findById(id)).willReturn(Mono.just(operationBdd));
        given(operationMapper.mapVersOperation(operationDto)).willReturn(operation);
        given(operationRepository.save(operation)).willReturn(Mono.just(operationReponse));
        given(operationMapper.mapVersOperationDto(operationReponse)).willReturn(operationDtoReponse);

        Mono<OperationDto> resultat = operationService.modifierOperationUtilisateur(operationDto);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectNextMatches(oDto -> oDto.getUtilisateurDto().getEmail().equals(email))
                .verifyComplete();
        then(operationRepository).should().findById(id);
        then(operationRepository).should().save(operation);
    }

    @Test
    public void modifierOperationUtilisateur_OperationNonTrouvee() {
        String id = "1";
        OperationDto operationDto = new OperationDto().setId(id);
        given(operationRepository.findById(id)).willReturn(Mono.error(new OperationNonTrouveException("")));

        Mono<OperationDto> resultat = operationService.modifierOperationUtilisateur(operationDto);

        StepVerifier.create(resultat)
                .expectSubscription()
                .expectError(OperationNonTrouveException.class)
                .verify();
    }

    /*@Test
    public void modifierOperationUtilisateur_OperationNonTrouvee() {
        String id = "1";
        OperationDto operationDto = new OperationDto().setId(id);
        given(operationRepository.findById(id)).willThrow(OperationNonTrouveException.class);

        Throwable thrown = catchThrowable(() -> operationService.modifierOperationUtilisateur(operationDto));

        assertThat(thrown).isNotNull();
        assertThat(thrown).isExactlyInstanceOf(OperationNonTrouveException.class);
        verifyZeroInteractions(operationMapper);
        then(operationRepository).should().findById(id);
        verifyNoMoreInteractions(operationRepository);
    }*/

    @Test
    public void supprimerOperation() {
        String id = "1";
        given(operationRepository.deleteById(id)).willReturn(Mono.empty());

        Mono<Void> resultat = operationService.supprimerOperation(id);

        StepVerifier.create(resultat)
                .expectSubscription()
                .verifyComplete();
        then(operationRepository).should().deleteById(id);
    }
}