package org.naukma.buonjourneyserver.service;


import org.naukma.buonjourneyserver.dto.EventDto;
import org.naukma.buonjourneyserver.dto.createDto.EventCreateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEventService {
    List<EventDto> getAllEventsByTripId(Long tripId);

    EventDto createEvent(EventCreateDto event);

    EventDto updateEvent(EventDto event);

    void deleteEvent(Long eventId);

    EventDto getEventById(Long eventId);
}
