package org.naukma.buonjourneyserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.naukma.buonjourneyserver.dto.EventDto;
import org.naukma.buonjourneyserver.entity.EventEntity;

@Mapper(componentModel = "spring")
public interface IEventMapper {
    IEventMapper INSTANCE = Mappers.getMapper(IEventMapper.class);
    EventDto entityToDto(EventEntity eventEntity);
    EventEntity dtoToEntity(EventDto eventDto);
}
