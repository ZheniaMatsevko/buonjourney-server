package org.naukma.buonjourneyserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.naukma.buonjourneyserver.dto.TicketDto;
import org.naukma.buonjourneyserver.entity.TicketEntity;

@Mapper(componentModel = "spring")
public interface ITicketMapper {
    ITicketMapper INSTANCE = Mappers.getMapper(ITicketMapper.class);
    TicketDto entityToDto(TicketEntity eventEntity);
    TicketEntity dtoToEntity(TicketDto eventDto);
}
