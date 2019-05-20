package com.cmarchive.bank.serviceutilisateur.controleur;

import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationPermanenteDto;
import com.cmarchive.bank.serviceutilisateur.service.OperationPermanenteService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class OperationPermanenteControleur {

    private OperationPermanenteService operationPermanenteService;

    public OperationPermanenteControleur(OperationPermanenteService operationPermanenteService) {
        this.operationPermanenteService = operationPermanenteService;
    }

    @GetMapping("/operations-permanentes/{utilisateurId}")
    @PreAuthorize("#oauth2.hasScope('USER')")
    public Flux<OperationPermanenteDto> listerOperationPermanenteUtilisateur(@PathVariable String utilisateurId) {
        return operationPermanenteService.listerOperationPermanentesParUtilisateur(utilisateurId)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @PostMapping("/operations-permanentes/{utilisateurId}")
    @PreAuthorize("#oauth2.hasScope('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationPermanenteDto> ajouterOperationPermanenteAUtilisateur(@PathVariable String utilisateurId,
                                                                               @RequestBody OperationPermanenteDto operationPermanenteDto) {
        return operationPermanenteService.ajouterOperationPermanenteAUtilisateur(utilisateurId,
                operationPermanenteDto)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @PutMapping("/operations-permanentes")
    @PreAuthorize("#oauth2.hasScope('USER')")
    public Mono<OperationPermanenteDto> modifierOperationPermanenteUtilisateur(@RequestBody OperationPermanenteDto operationPermanenteDto) {
        return operationPermanenteService.modifierOperationPermanenteUtilisateur(operationPermanenteDto)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @DeleteMapping("/operations-permanentes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("#oauth2.hasScope('USER')")
    public Mono<Void> supprimerOperationPermanenteUtilisateur(@PathVariable String id) {
        return operationPermanenteService.supprimerOperationPermanente(id);
    }
}
