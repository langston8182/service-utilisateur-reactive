package com.cmarchive.bank.serviceutilisateur.mapper;

import com.cmarchive.bank.serviceutilisateur.modele.Utilisateurs;
import com.cmarchive.bank.serviceutilisateur.modele.dto.UtilisateursDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UtilisateurMapper.class)
public interface UtilisateursMapper {

    @Mapping(target = "utilisateurs", source = "utilisateursDtos")
    Utilisateurs mapVersUtilisateurs(UtilisateursDto utilisateursDto);

    @Mapping(target = "utilisateursDtos", source = "utilisateurs")
    UtilisateursDto mapVersUtilisateursDto(Utilisateurs utilisateurs);

}
