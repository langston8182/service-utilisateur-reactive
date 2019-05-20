package com.cmarchive.bank.serviceutilisateur.mapper;

import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import com.cmarchive.bank.serviceutilisateur.modele.Utilisateurs;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateurDto;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateursDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UtilisateursMapperTest {

    @Autowired
    private UtilisateursMapper utilisateursMapper;

    @Configuration
    @ComponentScan(basePackageClasses = UtilisateursMapper.class, useDefaultFilters = false, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {UtilisateursMapper.class,
                    UtilisateurMapper.class})})
    public static class TestConfiguration {
        //
    }

    @Test
    public void mapVersUtilisateurs() {
        UtilisateurDto utilisateurDto = new UtilisateurDto()
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Marchive")
                .setPrenom("Cyril")
                .setMotDePasse("motDePasse");
        UtilisateursDto utilisateursDto = new UtilisateursDto()
                .setUtilisateursDtos(singletonList(utilisateurDto));

        Utilisateurs resultat = utilisateursMapper.mapVersUtilisateurs(utilisateursDto);

        assertThat(resultat.getUtilisateurs()).hasSize(1);
        assertThat(resultat.getUtilisateurs().get(0)).isEqualToComparingFieldByField(utilisateurDto);
    }

    @Test
    public void mapVersUtilisateursDto() {
        Utilisateur utilisateur = new Utilisateur()
                .setEmail("cyril.marchive@gmail.com")
                .setNom("Marchive")
                .setPrenom("Cyril")
                .setMotDePasse("motDePasse");
        Utilisateurs utilisateurs = new Utilisateurs()
                .setUtilisateurs(singletonList(utilisateur));

        UtilisateursDto resultat = utilisateursMapper.mapVersUtilisateursDto(utilisateurs);

        assertThat(resultat.getUtilisateursDtos()).hasSize(1);
        assertThat(resultat.getUtilisateursDtos().get(0)).isEqualToComparingFieldByField(utilisateur);
    }
}