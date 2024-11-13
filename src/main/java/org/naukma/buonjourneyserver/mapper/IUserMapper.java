package org.naukma.buonjourneyserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.naukma.buonjourneyserver.dto.UserDto;
import org.naukma.buonjourneyserver.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserDto entityToDto(UserEntity userEntity);

    UserEntity dtoToEntity(UserDto userDto);
}
