package com.cmarchive.bank.serviceutilisateur.mapper;

import com.cmarchive.bank.ressource.model.OperationPermanenteDto;
import com.cmarchive.bank.ressource.model.UtilisateurDto;
import com.cmarchive.bank.serviceutilisateur.modele.OperationPermanente;
import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class OperationPermanenteMapperTest {

    @Autowired
    private OperationPermanenteMapper operationPermanenteMapper;

    @Configuration
    @ComponentScan(basePackageClasses = {OperationPermanenteMapper.class, UtilisateurMapper.class},
            useDefaultFilters = false, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                    classes = {OperationPermanenteMapper.class, UtilisateurMapper.class}) })
    public static class TestConfiguration {
        //
    }

    @Test
    public void mapVersOperationPermanente() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationPermanenteDto operationPermanenteDto = creerOperationPermanenteDto(utilisateurDto);

        OperationPermanente resultat = operationPermanenteMapper.mapVersOperationPermanente(operationPermanenteDto);

        assertThat(resultat.getUtilisateur()).isEqualToComparingFieldByField(operationPermanenteDto.getUtilisateurDto());
        assertThat(resultat).isEqualToIgnoringGivenFields(operationPermanenteDto, "utilisateur");
    }

    @Test
    public void mapVersOperationPermanenteDto() {
        Utilisateur utilisateur = creerUtilisateur();
        OperationPermanente operationPermanente = creerOperationPermanente(utilisateur);

        OperationPermanenteDto resultat = operationPermanenteMapper.mapVersOperationPermanenteDto(operationPermanente);

        assertThat(resultat.getUtilisateurDto()).isEqualToComparingOnlyGivenFields(operationPermanente.getUtilisateur(),
                "nom", "prenom", "email");
        assertThat(resultat).isEqualToComparingOnlyGivenFields(operationPermanente,
                "intitule", "jour", "prix");
    }

    private OperationPermanente creerOperationPermanente(Utilisateur cyril) {
        return new OperationPermanente()
                .setJour(12)
                .setIntitule("operation")
                .setPrix(BigDecimal.TEN)
                .setUtilisateur(cyril);
    }

    private Utilisateur creerUtilisateur() {
        return new Utilisateur()
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Marchive")
                .setPrenom("Cyril");
    }

    private OperationPermanenteDto creerOperationPermanenteDto(UtilisateurDto cyril) {
        return new OperationPermanenteDto()
                .jour(12)
                .intitule("operation")
                .prix(BigDecimal.TEN)
                .utilisateurDto(cyril);
    }

    private UtilisateurDto creerUtilisateurDto() {
        return new UtilisateurDto()
                .email("cyril.marchive@gmail.com")
                .nom("Marchive")
                .prenom("Cyril");
    }
}