package com.cmarchive.bank.serviceutilisateur.mapper;

import com.cmarchive.bank.serviceutilisateur.modele.Operations;
import com.cmarchive.bank.serviceutilisateur.modele.dto.OperationsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OperationMapper.class)
public interface OperationsMapper {

    @Mapping(target = "operations", source = "operationDtos")
    Operations mapVersOperations(OperationsDto operationsDto);

    @Mapping(target = "operationDtos", source = "operations")
    OperationsDto mapVersOperationsDto(Operations operations);
}
