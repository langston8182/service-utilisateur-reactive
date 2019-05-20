package com.cmarchive.bank.serviceutilisateur.controleur;

import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationDto;
import com.cmarchive.bank.serviceutilisateur.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class OperationControleur {

    private OperationService operationService;

    public OperationControleur(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/operations/{utilisateurId}")
    @PreAuthorize("#oauth2.hasScope('USER')")
    public Flux<OperationDto> listerOperationUtilisateur(@PathVariable String utilisateurId) {
        return operationService.listerOperationsParUtilisateur(utilisateurId)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @PostMapping("/operations/{utilisateurId}")
    @PreAuthorize("#oauth2.hasScope('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationDto> ajouterOperationAUtilisateur(@PathVariable String utilisateurId,
                                                           @RequestBody OperationDto operationDto) {
        return operationService.ajouterOperationAUtilisateur(utilisateurId, operationDto)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @PutMapping("/operations")
    @PreAuthorize("#oauth2.hasScope('USER')")
    public Mono<OperationDto> modifierOperationUtilisateur(@RequestBody OperationDto operationDto) {
        return operationService.modifierOperationUtilisateur(operationDto)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @DeleteMapping("/operations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("#oauth2.hasScope('USER')")
    public Mono<Void> supprimerOperationUtilisateur(@PathVariable String id) {
        return operationService.supprimerOperation(id);
    }
}
