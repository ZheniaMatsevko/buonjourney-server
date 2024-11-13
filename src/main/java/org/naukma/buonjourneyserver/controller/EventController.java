package org.naukma.buonjourneyserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.EventDto;
import org.naukma.buonjourneyserver.service.IEventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final IEventService eventService;

    @PostMapping
    public EventDto createEvent(EventDto eventDto) {
        return eventService.addEvent(eventDto);
    }

    @PutMapping("/{id}")
    public EventDto updateEvent(@PathVariable Long id, EventDto eventDto) {
        return eventService.updateEvent(eventDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long id) {
        log.info("Deleting event with ID: {}", id);
        eventService.deleteEvent(id);
        log.info("Event deleted with ID: {}", id);
    }

    @GetMapping("/{eventId}")
    public EventDto getEventById(@PathVariable Long eventId) {
        log.info("Retrieving event with ID: {}", eventId);
        return eventService.getEventById(eventId);
    }

    @GetMapping
    public List<EventDto> getAll() {
        log.info("Getting all events");
        return eventService.getAll();
    }
}
