package org.naukma.buonjourneyserver.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.PlaceDto;
import org.naukma.buonjourneyserver.dto.UserDto;
import org.naukma.buonjourneyserver.dto.createDto.PlaceCreateDto;
import org.naukma.buonjourneyserver.entity.PlaceEntity;
import org.naukma.buonjourneyserver.mapper.IPlaceMapper;
import org.naukma.buonjourneyserver.mapper.IUserMapper;
import org.naukma.buonjourneyserver.repository.IPlaceRepository;
import org.naukma.buonjourneyserver.service.IPlaceService;
import org.naukma.buonjourneyserver.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService implements IPlaceService {
    private final IPlaceRepository placeRepository;
    private final IUserService userService;

    @Override
    public List<PlaceDto> getAllPlacesByUserId(Long userId) {
        List<PlaceEntity> placeEntities = placeRepository.findAllByUserId(userId);
        return placeEntities.stream().map(IPlaceMapper.INSTANCE::entityToDto).toList();
    }

    @Override
    public PlaceDto createPlace(PlaceCreateDto place) throws EntityNotFoundException{
        log.info("Creating place");

        UserDto user = userService.getUserById(place.getUserId());

        PlaceEntity placeToCreate = IPlaceMapper.INSTANCE.createDtoToEntity(place);
        placeToCreate.setUser(IUserMapper.INSTANCE.dtoToEntity(user));

        PlaceEntity createdPlace = placeRepository.save(placeToCreate);

        log.info("Place created successfully.");
        return IPlaceMapper.INSTANCE.entityToDto(createdPlace);
    }

    @Override
    public PlaceDto createPlace(PlaceDto place, UserDto user){
        log.info("Creating place");

        PlaceEntity placeToCreate = IPlaceMapper.INSTANCE.dtoToEntity(place);
        placeToCreate.setUser(IUserMapper.INSTANCE.dtoToEntity(user));

        PlaceEntity createdPlace = placeRepository.save(placeToCreate);

        log.info("Place created successfully.");
        return IPlaceMapper.INSTANCE.entityToDto(createdPlace);
    }

    @Override
    public PlaceDto updatePlace(PlaceDto place) throws EntityNotFoundException {
        Optional<PlaceEntity> optional = placeRepository.findById(place.getId());
        if (optional.isPresent()) {
            PlaceEntity placeEntity = optional.get();

            placeEntity.setAddress(place.getAddress() == null ? placeEntity.getAddress() : place.getAddress());
            placeEntity.setDescription(place.getDescription() == null ? placeEntity.getDescription() : place.getDescription());
            placeEntity.setName(place.getName() == null ? placeEntity.getName() : place.getName());

            PlaceEntity editedPlace = placeRepository.save(placeEntity);
            log.info("Updated place with id " + editedPlace.getId());

            return IPlaceMapper.INSTANCE.entityToDto(placeEntity);
        } else {
            throw new EntityNotFoundException("Place not found for editing");
        }
    }

    @Override
    public void deletePlace(Long placeId) throws EntityNotFoundException {
        if (placeRepository.existsById(placeId)) {
            placeRepository.deleteById(placeId);
            log.info("Deleted place with ID: {}", placeId);
        } else {
            throw new EntityNotFoundException("Place not found for deletion");
        }
    }

    @Override
    public PlaceDto getPlaceById(Long placeId) throws EntityNotFoundException {
        PlaceEntity placeEntity = placeRepository.findById(placeId).orElse(null);
        if (placeEntity != null) {
            log.info("Retrieved place with ID: {}", placeEntity.getId());
        } else {
            log.warn("PLace not found with ID: {}", placeId);
            throw new EntityNotFoundException("Place with id " + placeId + " not found");
        }
        return IPlaceMapper.INSTANCE.entityToDto(placeEntity);
    }

    @Override
    public List<PlaceDto> createMultiple(List<PlaceCreateDto> placeCreateDtos) {
        List<PlaceDto> places = new ArrayList<>();
        for(PlaceCreateDto placeCreateDto: placeCreateDtos){
            places.add(this.createPlace(placeCreateDto));
        }
        return places;
    }
}
