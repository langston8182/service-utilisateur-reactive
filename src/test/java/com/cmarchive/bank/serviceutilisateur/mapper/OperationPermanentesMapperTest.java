package com.cmarchive.bank.serviceutilisateur.mapper;

import com.cmarchive.bank.serviceutilisateur.modele.OperationPermanente;
import com.cmarchive.bank.serviceutilisateur.modele.OperationPermanentes;
import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationPermanenteDto;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationPermanentesDto;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateurDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class OperationPermanentesMapperTest {

    @Autowired
    private OperationPermanentesMapper operationPermanentesMapper;

    @Configuration
    @ComponentScan(basePackageClasses = OperationPermanentesMapper.class,
            useDefaultFilters = false, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {OperationPermanentesMapper.class,
                    OperationPermanenteMapper.class, UtilisateurMapper.class})})
    public static class TestConfiguration {
        //
    }

    @Test
    public void mapVersOperationPermanentes() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationPermanenteDto operationPermanenteDto = creerOperationPermanenteDto(utilisateurDto);
        OperationPermanentesDto operationPermanentesDto = new OperationPermanentesDto()
                .setOperationPermanenteDtos(singletonList(operationPermanenteDto));

        OperationPermanentes resultat = operationPermanentesMapper.mapVersOperationPermanentes(operationPermanentesDto);

        assertThat(resultat.getOperationPermanentes()).hasSize(1);
        assertThat(resultat.getOperationPermanentes().get(0)
                .getUtilisateur()).isEqualToComparingFieldByField(utilisateurDto);
        assertThat(resultat.getOperationPermanentes().get(0)).isEqualToIgnoringGivenFields(operationPermanenteDto, "utilisateur");
    }

    @Test
    public void mapVersOperationPermanentesDto() {
        Utilisateur utilisateur = creerUtilisateur();
        OperationPermanente operationPermanente = creerOperationPermanente(utilisateur);
        OperationPermanentes operationPermanentes = new OperationPermanentes()
                .setOperationPermanentes(singletonList(operationPermanente));

        OperationPermanentesDto resultat = operationPermanentesMapper.mapVersOperationPermanentesDto(operationPermanentes);

        assertThat(resultat.getOperationPermanenteDtos()).hasSize(1);
        assertThat(resultat.getOperationPermanenteDtos().get(0)
                .getUtilisateurDto()).isEqualToComparingFieldByField(utilisateur);
        assertThat(resultat.getOperationPermanenteDtos().get(0)).isEqualToIgnoringGivenFields(operationPermanente, "utilisateurDto");
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
                .setJour(12)
                .setIntitule("operation")
                .setPrix(BigDecimal.TEN)
                .setUtilisateurDto(cyril);
    }

    private UtilisateurDto creerUtilisateurDto() {
        return new UtilisateurDto()
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Marchive")
                .setPrenom("Cyril");
    }
}