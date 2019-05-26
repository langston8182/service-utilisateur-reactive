package com.cmarchive.bank.serviceutilisateur.configuration;

import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateurDto;
import com.cmarchive.bank.serviceutilisateur.service.UtilisateurService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private UtilisateurService utilisateurService;

    public CommandLineAppStartupRunner(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public void run(String... args) {
        UtilisateurDto utilisateurDto = new UtilisateurDto()
                .setId("1")
                .setNom("Marchive")
                .setPrenom("Cyril")
                .setEmail("cyril.marchive@gmail.com");

        utilisateurService.creerUtilisateur(utilisateurDto).subscribe();
    }
}
