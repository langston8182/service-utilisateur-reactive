package com.cmarchive.bank.serviceutilisateur.mapper;

import com.cmarchive.bank.serviceutilisateur.modele.OperationPermanente;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationPermanenteDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UtilisateurMapper.class)
public interface OperationPermanenteMapper {

    @Mapping(target = "utilisateur", source = "utilisateurDto")
    OperationPermanente mapVersOperationPermanente(OperationPermanenteDto operationPermanenteDto);

    @InheritInverseConfiguration
    OperationPermanenteDto mapVersOperationPermanenteDto(OperationPermanente operationPermanente);
}
