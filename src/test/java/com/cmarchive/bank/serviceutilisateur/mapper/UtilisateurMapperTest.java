package com.cmarchive.bank.serviceutilisateur.mapper;

import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateurDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UtilisateurMapperTest {

    @Autowired
    private UtilisateurMapper utilisateurMapper;

    @Configuration
    @ComponentScan(basePackageClasses = UtilisateurMapper.class, useDefaultFilters = false, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = UtilisateurMapper.class) })
    public static class TestConfiguration {
        //
    }

    @Test
    public void mapVersUtilisateur() {
        UtilisateurDto utilisateurDto = new UtilisateurDto()
                .setNom("Marchive")
                .setEmail("cyril.marchive@gmail.com")
                .setPrenom("Cyril")
                .setMotDePasse("motDePasse");

        Utilisateur resultat = utilisateurMapper.mapVersUtilisateur(utilisateurDto);

        assertThat(resultat).isEqualToComparingFieldByField(utilisateurDto);
    }

    @Test
    public void mapVersUtilisateurDto() {
        Utilisateur utilisateur = new Utilisateur()
                .setNom("Marchive")
                .setEmail("cyril.marchive@gmail.com")
                .setPrenom("Cyril")
                .setMotDePasse("motDePasse");

        UtilisateurDto resultat = utilisateurMapper.mapVersUtilisateurDto(utilisateur);

        assertThat(resultat).isEqualToComparingFieldByField(utilisateur);
    }
}