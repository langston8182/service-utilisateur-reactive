package com.cmarchive.bank.serviceutilisateur.mapper;

import com.cmarchive.bank.serviceutilisateur.modele.Utilisateur;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateurDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {

    @Mapping(target = "id", source = "identifiant")
    Utilisateur mapVersUtilisateur(UtilisateurDto utilisateurDto);

    @InheritInverseConfiguration
    UtilisateurDto mapVersUtilisateurDto(Utilisateur utilisateur);

}
