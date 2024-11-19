package org.naukma.buonjourneyserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.naukma.buonjourneyserver.dto.ItemDto;
import org.naukma.buonjourneyserver.dto.createDto.ItemCreateDto;
import org.naukma.buonjourneyserver.entity.ItemEntity;

@Mapper(componentModel = "spring")
public interface IItemMapper {
    IItemMapper INSTANCE = Mappers.getMapper(IItemMapper.class);

    ItemDto entityToDto(ItemEntity eventEntity);

    ItemEntity dtoToEntity(ItemDto eventDto);

    @Mapping(target = "id", ignore = true)
    ItemEntity createDtoToEntity(ItemCreateDto createDto);
}
