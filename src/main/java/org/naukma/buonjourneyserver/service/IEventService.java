package org.naukma.buonjourneyserver.service;


import org.naukma.buonjourneyserver.dto.EventDto;
import org.naukma.buonjourneyserver.dto.TripDto;
import org.naukma.buonjourneyserver.dto.createDto.EventCreateDto;

import java.util.List;

public interface IEventService {
    List<EventDto> getAllEventsByTripId(Long tripId);

    EventDto createEvent(EventCreateDto event);
    EventDto createEvent(EventDto event, TripDto trip);

    EventDto updateEvent(EventDto event);

    void deleteEvent(Long eventId);

    EventDto getEventById(Long eventId);

    List<EventDto> createMultiple(List<EventCreateDto> eventCreateDtos);
}
