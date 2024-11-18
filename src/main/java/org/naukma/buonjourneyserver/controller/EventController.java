package org.naukma.buonjourneyserver.controller;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.EventDto;
import org.naukma.buonjourneyserver.dto.createDto.EventCreateDto;
import org.naukma.buonjourneyserver.exceptions.ExceptionHelper;
import org.naukma.buonjourneyserver.service.IEventService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final IEventService eventService;

    @PostMapping
    public EventDto createEvent(@RequestBody @Valid EventCreateDto eventCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        EventDto eventDto = eventService.createEvent(eventCreateDto);
        log.info("Event created with ID: {}", eventDto.getId());
        return eventDto;
    }

    @PostMapping("/multiple")
    public List<EventDto> createEvents(@RequestBody @Valid List<EventCreateDto> eventCreateDtos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        return eventService.createMultiple(eventCreateDtos);
    }

    @PutMapping("/{id}")
    public EventDto updateEvent(@PathVariable Long id, @RequestBody @Valid EventDto eventUpdateDto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        eventUpdateDto.setId(id);
        EventDto eventDto = eventService.updateEvent(eventUpdateDto);
        log.info("Event updated with ID: {}", eventDto.getId());
        return eventDto;
    }

    @DeleteMapping("/{id}")
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
    public List<EventDto> getAllEventsByTripId(@RequestParam Long tripId) {
        log.info("Retrieving all events for trip with ID: {}", tripId);
        return eventService.getAllEventsByTripId(tripId);
    }
}
