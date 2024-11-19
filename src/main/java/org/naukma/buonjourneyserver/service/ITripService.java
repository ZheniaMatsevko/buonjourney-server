package org.naukma.buonjourneyserver.service;


import org.naukma.buonjourneyserver.dto.TripDto;
import org.naukma.buonjourneyserver.dto.UserDto;
import org.naukma.buonjourneyserver.dto.createDto.TripCreateDto;
import org.naukma.buonjourneyserver.dto.updateDto.TripUpdateDto;

import java.util.List;

public interface ITripService {
    List<TripDto> getAllByUserId(Long userId);

    TripDto createTrip(TripCreateDto tripDto);
    TripDto createTrip(TripDto tripDto, UserDto userDto);

    TripDto updateTrip(TripUpdateDto tripDto);

    void deleteTrip(Long tripId);

    TripDto getTripById(Long tripId);
}
