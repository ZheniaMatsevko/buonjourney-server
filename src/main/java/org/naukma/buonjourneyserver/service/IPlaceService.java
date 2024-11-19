package org.naukma.buonjourneyserver.service;


import org.naukma.buonjourneyserver.dto.PlaceDto;
import org.naukma.buonjourneyserver.dto.UserDto;
import org.naukma.buonjourneyserver.dto.createDto.PlaceCreateDto;

import java.util.List;

public interface IPlaceService {
    List<PlaceDto> getAllPlacesByUserId(Long userId);

    PlaceDto createPlace(PlaceCreateDto place);
    PlaceDto createPlace(PlaceDto place, UserDto user);

    PlaceDto updatePlace(PlaceDto place);

    void deletePlace(Long placeId);

    PlaceDto getPlaceById(Long placeId);

    List<PlaceDto> createMultiple(List<PlaceCreateDto> placeCreateDtos);

}
