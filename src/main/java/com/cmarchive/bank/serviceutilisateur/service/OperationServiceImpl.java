package com.cmarchive.bank.serviceutilisateur.service;

import com.cmarchive.bank.serviceutilisateur.exception.OperationNonTrouveException;
import com.cmarchive.bank.serviceutilisateur.mapper.OperationMapper;
import com.cmarchive.bank.serviceutilisateur.mapper.UtilisateurMapper;
import com.cmarchive.bank.serviceutilisateur.modele.Operation;
import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationDto;
import com.cmarchive.bank.serviceutilisateur.repository.OperationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OperationServiceImpl implements OperationService {

    private UtilisateurService utilisateurService;
    private OperationRepository operationRepository;
    private UtilisateurMapper utilisateurMapper;
    private OperationMapper operationMapper;

    public OperationServiceImpl(UtilisateurService utilisateurService,
                                OperationRepository operationRepository,
                                UtilisateurMapper utilisateurMapper,
                                OperationMapper operationMapper) {
        this.utilisateurService = utilisateurService;
        this.operationRepository = operationRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.operationMapper = operationMapper;
    }

    @Override
    public Flux<OperationDto> listerOperationsParUtilisateur(String utilisateurId) {
        return utilisateurService.recupererUtilisateur(utilisateurId)
                .thenMany(operationRepository.findAllByUtilisateur_IdOrderByDateOperationDesc(utilisateurId))
                .map(operation -> operationMapper.mapVersOperationDto(operation));
    }

    @Override
    public Mono<OperationDto> ajouterOperationAUtilisateur(String utilisateurId, OperationDto operationDto) {
        return recupererUtilisateurParId(utilisateurId)
                .map(utilisateur -> operationMapper.mapVersOperation(operationDto).setUtilisateur(utilisateur))
                .flatMap(operation -> operationRepository.save(operation))
                .map(operation -> operationMapper.mapVersOperationDto(operation));
    }

    @Override
    public Mono<OperationDto> modifierOperationUtilisateur(OperationDto operationDto) {
        return recupererOperationDansBdd(operationDto)
                .map(operation -> operationMapper
                        .mapVersOperation(operationDto).setUtilisateur(operation.getUtilisateur()))
                .flatMap(operation -> operationRepository.save(operation))
                .map(operation -> operationMapper.mapVersOperationDto(operation));
    }

    private Mono<Operation> recupererOperationDansBdd(OperationDto operationDto) {
        return operationRepository.findById(operationDto.getId())
                .switchIfEmpty(Mono.error(new OperationNonTrouveException("Operation non trouvee")));
    }

    @Override
    public Mono<Void> supprimerOperation(String id) {
        return operationRepository.deleteById(id);
    }

    private Mono<Utilisateur> recupererUtilisateurParId(String utilisateurId) {
        return utilisateurService.recupererUtilisateur(utilisateurId)
                .map(utilisateurDto -> utilisateurMapper.mapVersUtilisateur(utilisateurDto));
    }
}
