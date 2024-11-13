package org.naukma.buonjourneyserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.naukma.buonjourneyserver.dto.TripDto;
import org.naukma.buonjourneyserver.entity.TripEntity;

@Mapper(componentModel = "spring")
public interface ITripMapper {
    ITripMapper INSTANCE = Mappers.getMapper(ITripMapper.class);
    TripDto entityToDto(TripEntity eventEntity);
    TripEntity dtoToEntity(TripDto eventDto);
}
