package com.cmarchive.bank.serviceutilisateur.mapper;

import com.cmarchive.bank.serviceutilisateur.modele.Operation;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UtilisateurMapper.class)
public interface OperationMapper {

    @Mapping(target = "utilisateur", source = "utilisateurDto")
    Operation mapVersOperation(OperationDto operationDto);

    @InheritInverseConfiguration
    OperationDto mapVersOperationDto(Operation operation);

}
