package org.naukma.buonjourneyserver.service;


import org.naukma.buonjourneyserver.dto.EventDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEventService {
    List<EventDto> getAll();

    EventDto addEvent(EventDto event);

    EventDto updateEvent(EventDto event);

    void deleteEvent(Long eventId);

    EventDto getEventById(Long eventId);
}
