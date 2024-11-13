package org.naukma.buonjourneyserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.naukma.buonjourneyserver.dto.PackingListDto;
import org.naukma.buonjourneyserver.entity.PackingListEntity;

@Mapper(componentModel = "spring")
public interface IPackingListMapper {
    IPackingListMapper INSTANCE = Mappers.getMapper(IPackingListMapper.class);
    PackingListDto entityToDto(PackingListEntity eventEntity);
    PackingListEntity dtoToEntity(PackingListDto eventDto);
}
