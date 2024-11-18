package org.naukma.buonjourneyserver.service.implementation;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.EventDto;
import org.naukma.buonjourneyserver.dto.TripDto;
import org.naukma.buonjourneyserver.dto.createDto.EventCreateDto;
import org.naukma.buonjourneyserver.entity.EventEntity;
import org.naukma.buonjourneyserver.mapper.IEventMapper;
import org.naukma.buonjourneyserver.mapper.ITripMapper;
import org.naukma.buonjourneyserver.repository.IEventRepository;
import org.naukma.buonjourneyserver.service.IEventService;
import org.naukma.buonjourneyserver.service.ITripService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService implements IEventService {

    private final IEventRepository eventRepository;
    private final ITripService tripService;

    @Override
    public EventDto createEvent(EventCreateDto event) throws EntityNotFoundException{
        log.info("Creating event");

        TripDto tripDto = tripService.getTripById(event.getTripId());

        EventEntity eventToCreate = IEventMapper.INSTANCE.createDtoToEntity(event);
        eventToCreate.setTrip(ITripMapper.INSTANCE.dtoToEntity(tripDto));

        EventEntity createdEvent = eventRepository.save(eventToCreate);

        log.info("Event created successfully.");
        return IEventMapper.INSTANCE.entityToDto(createdEvent);
    }

    @Transactional
    @Override
    public EventDto updateEvent(EventDto event) throws EntityNotFoundException {
        Optional<EventEntity> optional = eventRepository.findById(event.getId());
        if (optional.isPresent()) {
            EventEntity eventEntity = optional.get();

            eventEntity.setAddress(event.getAddress() == null ? eventEntity.getAddress() : event.getAddress());
            eventEntity.setTitle(event.getTitle() == null ? eventEntity.getTitle() : event.getTitle());
            eventEntity.setDateTime(event.getDateTime() == null ? eventEntity.getDateTime() : event.getDateTime());
            eventEntity.setDescription(event.getDescription() == null ? eventEntity.getDescription() : event.getDescription());

            EventEntity editedEvent = eventRepository.save(eventEntity);
            log.info("Updated event with id " + editedEvent.getId());

            return IEventMapper.INSTANCE.entityToDto(editedEvent);
        } else {
            throw new EntityNotFoundException("Event not found for editing");
        }
    }

    @Override
    public void deleteEvent(Long eventId) throws EntityNotFoundException {
        if (eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
            log.info("Deleted event with ID: {}", eventId);
        } else {
            throw new EntityNotFoundException("Event not found for deletion");
        }
    }

    @Override
    public List<EventDto> getAllEventsByTripId(Long tripId) {
        List<EventEntity> eventEntities = eventRepository.findAllByTripId(tripId);
        return eventEntities.stream().map(IEventMapper.INSTANCE::entityToDto).toList();
    }

    @Override
    public EventDto getEventById(Long eventId) throws EntityNotFoundException {
        EventEntity event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            log.info("Retrieved event with ID: {}", event.getId());
        } else {
            log.warn("Event not found with ID: {}", eventId);
            throw new EntityNotFoundException("Event with id " + eventId + " not found");
        }
        return IEventMapper.INSTANCE.entityToDto(event);
    }

    @Override
    public List<EventDto> createMultiple(List<EventCreateDto> eventCreateDtos) {
        List<EventDto> events = new ArrayList<>();
        for(EventCreateDto eventCreateDto: eventCreateDtos){
            events.add(this.createEvent(eventCreateDto));
        }
        return events;
    }
}
