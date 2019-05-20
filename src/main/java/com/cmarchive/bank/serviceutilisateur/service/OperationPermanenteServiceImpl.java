package com.cmarchive.bank.serviceutilisateur.service;

import com.cmarchive.bank.serviceutilisateur.exception.OperationNonTrouveException;
import com.cmarchive.bank.serviceutilisateur.mapper.OperationPermanenteMapper;
import com.cmarchive.bank.serviceutilisateur.mapper.UtilisateurMapper;
import com.cmarchive.bank.serviceutilisateur.modele.OperationPermanente;
import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationPermanenteDto;
import com.cmarchive.bank.serviceutilisateur.repository.OperationPermanenteRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OperationPermanenteServiceImpl implements OperationPermanenteService {

    private UtilisateurService utilisateurService;
    private OperationPermanenteRepository operationPermanenteRepository;
    private OperationPermanenteMapper operationPermanenteMapper;
    private UtilisateurMapper utilisateurMapper;

    public OperationPermanenteServiceImpl(UtilisateurService utilisateurService,
                                          OperationPermanenteRepository operationPermanenteRepository,
                                          OperationPermanenteMapper operationPermanenteMapper,
                                          UtilisateurMapper utilisateurMapper) {
        this.utilisateurService = utilisateurService;
        this.operationPermanenteRepository = operationPermanenteRepository;
        this.operationPermanenteMapper = operationPermanenteMapper;
        this.utilisateurMapper = utilisateurMapper;
    }

    @Override
    public Flux<OperationPermanenteDto> listerOperationPermanentesParUtilisateur(String utilisateurId) {
        return utilisateurService.recupererUtilisateur(utilisateurId)
                .thenMany(operationPermanenteRepository.findAllByUtilisateur_Id(utilisateurId))
                .map(operationPermanente -> operationPermanenteMapper.mapVersOperationPermanenteDto(operationPermanente));
    }

    @Override
    public Mono<OperationPermanenteDto> ajouterOperationPermanenteAUtilisateur(String utilisateurId, OperationPermanenteDto operationPermanenteDto) {
        return recupererUtilisateurParId(utilisateurId)
                .map(utilisateur -> operationPermanenteMapper
                        .mapVersOperationPermanente(operationPermanenteDto).setUtilisateur(utilisateur))
                .flatMap(operationPermanente -> operationPermanenteRepository.save(operationPermanente))
                .map(operationPermanente -> operationPermanenteMapper.mapVersOperationPermanenteDto(operationPermanente));
    }

    @Override
    public Mono<OperationPermanenteDto> modifierOperationPermanenteUtilisateur(OperationPermanenteDto operationPermanenteDto) {
        return recupererOperationPermanenteDansBdd(operationPermanenteDto)
                .map(op -> operationPermanenteMapper.mapVersOperationPermanente(operationPermanenteDto)
                        .setUtilisateur(op.getUtilisateur()))
                .flatMap(op -> operationPermanenteRepository.save(op))
                .map(op -> operationPermanenteMapper.mapVersOperationPermanenteDto(op));
    }

    @Override
    public Mono<Void> supprimerOperationPermanente(String id) {
        return operationPermanenteRepository
                .deleteById(id);
    }

    private Mono<Utilisateur> recupererUtilisateurParId(String utilisateurId) {
        return utilisateurService.recupererUtilisateur(utilisateurId)
                .map(utilisateurDto -> utilisateurMapper.mapVersUtilisateur(utilisateurDto));
    }

    private Mono<OperationPermanente> recupererOperationPermanenteDansBdd(OperationPermanenteDto operationPermanenteDto) {
        return operationPermanenteRepository.findById(operationPermanenteDto.getId())
                .switchIfEmpty(Mono.error(new OperationNonTrouveException("Operation permanente non trouvee")));
    }
}
