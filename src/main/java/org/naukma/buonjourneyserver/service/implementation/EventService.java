package org.naukma.buonjourneyserver.service.implementation;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.EventDto;
import org.naukma.buonjourneyserver.entity.EventEntity;
import org.naukma.buonjourneyserver.mapper.IEventMapper;
import org.naukma.buonjourneyserver.repository.IEventRepository;
import org.naukma.buonjourneyserver.service.IEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService implements IEventService {

    private final IEventRepository eventRepository;

    public List<EventDto> getAll() {
        return eventRepository.findAll().stream().map(IEventMapper.INSTANCE::entityToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventDto addEvent(EventDto event) {
        log.info("Creating event");
        EventEntity createdEvent = eventRepository.save(IEventMapper.INSTANCE.dtoToEntity(event));
        log.info("Event created successfully.");
        return IEventMapper.INSTANCE.entityToDto(createdEvent);
    }

    @Transactional
    @Override
    public EventDto updateEvent(EventDto event) {
        Optional<EventEntity> optional = eventRepository.findById(event.getId());
        /*if(optional.isPresent()){
            EventEntity eventEntity = optional.get();
            eventEntity.setCapacity(event.getCapacity());
            eventEntity.setDescription(event.getDescription());
            eventEntity.setCaption(event.getCaption());
            eventEntity.setAddress(IAddressMapper.INSTANCE.dtoToEntity(event.getAddress()));
            eventEntity.setDateTime(event.getDateTime());
            eventEntity.setPrice(event.getPrice());
            eventEntity.setOnline(event.isOnline());
            EventEntity editedEvent = eventRepository.save(eventEntity);
            log.info("Updating event with id "+editedEvent.getId());
            try {
                if(file!=null && !file.isEmpty()){
                    String imagePath= ImagesManager.saveEventImage(file,event.getOrganiser().getId(),editedEvent.getId());
                    eventRepository.updateImageUrl(editedEvent.getId(),imagePath);
                    editedEvent.setImageUrl(imagePath);
                }
                log.info("Event edited successfully.");
                return IEventMapper.INSTANCE.entityToDto(editedEvent);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }else{
            throw new EntityNotFoundException("Event not found for editing");
        }*/
        return null;
    }

    @Override
    public void deleteEvent(Long eventId) {
        if (eventRepository.existsById(eventId)) {
            Optional<EventEntity> event = eventRepository.findById(eventId);
            eventRepository.deleteById(eventId);
            log.info("Deleted event with ID: {}", eventId);
        } else {
            log.warn("Event not found for deletion with ID: {}", eventId);
        }
    }

    @Override
    public EventDto getEventById(Long eventId) {
        EventEntity event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            log.info("Retrieved event with ID: {}", event.getId());
        } else {
            log.warn("Event not found with ID: {}", eventId);
            throw new EntityNotFoundException("Event with id " + eventId + " not found");
        }
        return IEventMapper.INSTANCE.entityToDto(event);
    }
}
