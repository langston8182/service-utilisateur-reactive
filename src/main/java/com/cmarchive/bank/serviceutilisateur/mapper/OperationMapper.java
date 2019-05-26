package com.cmarchive.bank.serviceutilisateur.mapper;

import com.cmarchive.bank.ressource.model.OperationDto;
import com.cmarchive.bank.serviceutilisateur.modele.Operation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UtilisateurMapper.class)
public interface OperationMapper {

    @Mapping(target = "utilisateur", source = "utilisateurDto")
    @Mapping(target = "id", source = "identifiant")
    Operation mapVersOperation(OperationDto operationDto);

    @InheritInverseConfiguration
    OperationDto mapVersOperationDto(Operation operation);

}
