package org.naukma.buonjourneyserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.naukma.buonjourneyserver.dto.PlaceDto;
import org.naukma.buonjourneyserver.entity.PlaceEntity;

@Mapper(componentModel = "spring")
public interface IPlaceMapper {
    IPlaceMapper INSTANCE = Mappers.getMapper(IPlaceMapper.class);
    PlaceDto entityToDto(PlaceEntity eventEntity);
    PlaceEntity dtoToEntity(PlaceDto eventDto);
}
