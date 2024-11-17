package org.naukma.buonjourneyserver.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.TripDto;
import org.naukma.buonjourneyserver.dto.UserDto;
import org.naukma.buonjourneyserver.dto.createDto.TripCreateDto;
import org.naukma.buonjourneyserver.dto.updateDto.TripUpdateDto;
import org.naukma.buonjourneyserver.entity.TripEntity;
import org.naukma.buonjourneyserver.mapper.ITripMapper;
import org.naukma.buonjourneyserver.mapper.IUserMapper;
import org.naukma.buonjourneyserver.repository.ITripRepository;
import org.naukma.buonjourneyserver.service.ITripService;
import org.naukma.buonjourneyserver.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripService  implements ITripService {
    private final ITripRepository tripRepository;
    private final IUserService userService;


    @Override
    public List<TripDto> getAllByUserId(Long userId) {
        List<TripEntity> tripEntities = tripRepository.findAllByUserId(userId);
        return tripEntities.stream().map(ITripMapper.INSTANCE::entityToDto).toList();
    }

    @Override
    @Transactional
    public TripDto createTrip(TripCreateDto tripDto) {
        log.info("Creating trip");

        UserDto user = userService.getUserById(tripDto.getUserId());

        TripEntity tripToCreate = ITripMapper.INSTANCE.createDtoToEntity(tripDto);

        tripToCreate.setEvents(new ArrayList<>());
        tripToCreate.setTickets(new ArrayList<>());
        tripToCreate.setPackingLists(new ArrayList<>());
        tripToCreate.setUser(IUserMapper.INSTANCE.dtoToEntity(user));

        TripEntity createdTrip = tripRepository.save(tripToCreate);

        log.info("Trip created successfully.");
        return ITripMapper.INSTANCE.entityToDto(createdTrip);
    }

    @Override
    public TripDto updateTrip(TripUpdateDto tripDto) throws EntityNotFoundException{
        Optional<TripEntity> optional = tripRepository.findById(tripDto.getId());
        if (optional.isPresent()) {
            TripEntity tripEntity = optional.get();

            tripEntity.setDestination(tripDto.getDestination() == null ? tripEntity.getDestination() : tripDto.getDestination());
            tripEntity.setTitle(tripDto.getTitle() == null ? tripEntity.getTitle() : tripDto.getTitle());
            tripEntity.setStartDate(tripDto.getStartDate() == null ? tripEntity.getStartDate() : tripDto.getStartDate());
            tripEntity.setEndDate(tripDto.getEndDate() == null ? tripEntity.getEndDate() : tripDto.getEndDate());
            tripEntity.setStatus(tripDto.getStatus() == null ? tripEntity.getStatus() : tripDto.getStatus());

            TripEntity editedTrip = tripRepository.save(tripEntity);
            log.info("Updated trip with id " + editedTrip.getId());

            return ITripMapper.INSTANCE.entityToDto(tripEntity);
        } else {
            throw new EntityNotFoundException("Trip not found for editing");
        }
    }

    @Override
    public void deleteTrip(Long tripId) throws EntityNotFoundException{
        if (tripRepository.existsById(tripId)) {
            tripRepository.deleteById(tripId);
            log.info("Deleted trip with ID: {}", tripId);
        } else {
            throw new EntityNotFoundException("Trip not found for deletion");
        }
    }

    @Override
    public TripDto getTripById(Long tripId) throws EntityNotFoundException{
        TripEntity trip = tripRepository.findById(tripId).orElse(null);
        if (trip != null) {
            log.info("Retrieved trip with ID: {}", trip.getId());
        } else {
            log.warn("Trip not found with ID: {}", tripId);
            throw new EntityNotFoundException("Trip with id " + tripId + " not found");
        }
        return ITripMapper.INSTANCE.entityToDto(trip);
    }
}
