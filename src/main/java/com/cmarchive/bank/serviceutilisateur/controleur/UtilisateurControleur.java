package com.cmarchive.bank.serviceutilisateur.controleur;

import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationDto;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationPermanenteDto;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateurDto;
import com.cmarchive.bank.serviceutilisateur.service.OperationPermanenteService;
import com.cmarchive.bank.serviceutilisateur.service.OperationService;
import com.cmarchive.bank.serviceutilisateur.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class UtilisateurControleur {

    private UtilisateurService utilisateurService;
    private OperationPermanenteService operationPermanenteService;
    private OperationService operationService;

    public UtilisateurControleur(UtilisateurService utilisateurService,
                                 OperationPermanenteService operationPermanenteService,
                                 OperationService operationService) {
        this.utilisateurService = utilisateurService;
        this.operationPermanenteService = operationPermanenteService;
        this.operationService = operationService;
    }

    @GetMapping("/utilisateurs/")
    @PreAuthorize("#oauth2.hasScope('ADMIN')")
    public Flux<UtilisateurDto> listerUtilisateur() {
        return utilisateurService.listerUtilisateurs();
    }

    @GetMapping("/utilisateurs/{id}")
    @PreAuthorize("#oauth2.hasScope('ADMIN')")
    public Mono<UtilisateurDto> recupererUtilisateur(@PathVariable String id) {
        return utilisateurService.recupererUtilisateur(id)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @GetMapping("/utilisateurs")
    public Mono<UtilisateurDto> recupererUtilisateurParEmail(@RequestParam String email) {
        return utilisateurService.recupererUtilisateurParEmail(email)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @PostMapping("/utilisateurs/")
    @PreAuthorize("#oauth2.hasScope('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UtilisateurDto> sauvegarderUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        return utilisateurService.creerUtilisateur(utilisateurDto)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, throwable.getMessage(), throwable)));
    }

    @PutMapping("/utilisateurs/")
    @PreAuthorize("#oauth2.hasScope('ADMIN')")
    public Mono<UtilisateurDto> modifierUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        return utilisateurService.modifierUtilisateur(utilisateurDto)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @DeleteMapping("/utilisateurs/{id}")
    @PreAuthorize("#oauth2.hasScope('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> supprimerUtilisateur(@PathVariable String id) {
        return utilisateurService.supprimerUtilisateur(id)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @GetMapping("/utilisateurs/{idUtilisateur}/operations-permanentes/")
    @PreAuthorize("#oauth2.hasScope('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Flux<OperationPermanenteDto> listerOperationPermanenteUtilisateur(@PathVariable String idUtilisateur) {
        return operationPermanenteService.listerOperationPermanentesParUtilisateur(idUtilisateur)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @GetMapping("/utilisateurs/{idUtilisateur}/operations-permanentes/{idOperationPermanente}")
    @PreAuthorize("#oauth2.hasScope('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OperationPermanenteDto> recupererOperationPermanenteParUtilisateur
            (@PathVariable String idUtilisateur, @PathVariable String idOperationPermanente) {
        return operationPermanenteService.recupererOperationPermanenteParUtilisateur(idUtilisateur, idOperationPermanente)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @GetMapping("/utilisateurs/{idUtilisateur}/operations/")
    @PreAuthorize("#oauth2.hasScope('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Flux<OperationDto> listerOperationUtilisateur(@PathVariable String idUtilisateur) {
        return operationService.listerOperationsParUtilisateur(idUtilisateur)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }

    @GetMapping("/utilisateurs/{idUtilisateur}/operations/{idOperation}")
    @PreAuthorize("#oauth2.hasScope('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OperationDto> recupererOperationParUtilisateur(@PathVariable String idUtilisateur, @PathVariable String idOperation) {
        return operationService.recupererOperationParUtilisateur(idUtilisateur, idOperation)
                .onErrorResume(throwable -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, throwable.getMessage(), throwable)));
    }
}
