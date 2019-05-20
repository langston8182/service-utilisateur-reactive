package com.cmarchive.bank.serviceutilisateur.mapper;

import com.cmarchive.bank.serviceutilisateur.modele.OperationPermanentes;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationPermanentesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OperationPermanenteMapper.class)
public interface OperationPermanentesMapper {

    @Mapping(target = "operationPermanentes", source = "operationPermanenteDtos")
    OperationPermanentes mapVersOperationPermanentes(OperationPermanentesDto operationPermanentesDto);

    @Mapping(target = "operationPermanenteDtos", source = "operationPermanentes")
    OperationPermanentesDto mapVersOperationPermanentesDto(OperationPermanentes operationPermanentes);
}
