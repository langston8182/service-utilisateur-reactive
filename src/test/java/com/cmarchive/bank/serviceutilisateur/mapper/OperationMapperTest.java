package com.cmarchive.bank.serviceutilisateur.mapper;

import com.cmarchive.bank.serviceutilisateur.modele.Operation;
import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationDto;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateurDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class OperationMapperTest {

    @Autowired
    private OperationMapper operationMapper;

    @Configuration
    @ComponentScan(basePackageClasses = {OperationMapper.class, UtilisateurMapper.class},
            useDefaultFilters = false, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                    classes = {OperationMapper.class, UtilisateurMapper.class}) })
    public static class TestConfiguration {
        //
    }

    @Test
    public void mapVersOperation() {
        UtilisateurDto utilisateurDto = creerUtilisateurDto();
        OperationDto operationDto = creerOperationDto(utilisateurDto);

        Operation resultat = operationMapper.mapVersOperation(operationDto);

        assertThat(resultat.getUtilisateur()).isEqualToComparingFieldByField(operationDto.getUtilisateurDto());
        assertThat(resultat).isEqualToIgnoringGivenFields(operationDto, "utilisateur");
    }

    @Test
    public void mapVersOperationDto() {
        Utilisateur utilisateur = creerUtilisateur();
        Operation operation = creerOperation(utilisateur);

        OperationDto resultat = operationMapper.mapVersOperationDto(operation);

        assertThat(resultat.getUtilisateurDto()).isEqualToComparingFieldByField(operation.getUtilisateur());
        assertThat(resultat).isEqualToIgnoringGivenFields(operation, "utilisateurDto");
    }

    private Operation creerOperation(Utilisateur cyril) {
        return new Operation()
                .setDateOperation(LocalDate.now())
                .setIntitule("operation")
                .setPrix(BigDecimal.TEN)
                .setUtilisateur(cyril);
    }

    private Utilisateur creerUtilisateur() {
        return new Utilisateur()
                .setIdOkta("1")
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Marchive")
                .setPrenom("Cyril");
    }

    private OperationDto creerOperationDto(UtilisateurDto cyril) {
        return new OperationDto()
                .setDateOperation(LocalDate.now())
                .setIntitule("operation")
                .setPrix(BigDecimal.TEN)
                .setUtilisateurDto(cyril);
    }

    private UtilisateurDto creerUtilisateurDto() {
        return new UtilisateurDto()
                .setIdOkta("1")
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Marchive")
                .setPrenom("Cyril");
    }

}