package org.naukma.buonjourneyserver.controller;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.PlaceDto;
import org.naukma.buonjourneyserver.dto.createDto.PlaceCreateDto;
import org.naukma.buonjourneyserver.exceptions.ExceptionHelper;
import org.naukma.buonjourneyserver.service.IPlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {

    private final IPlaceService placeService;

    @PostMapping
    public PlaceDto createPlace(@RequestBody @Valid PlaceCreateDto placeCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        PlaceDto placeDto = placeService.createPlace(placeCreateDto);
        log.info("Place created with ID: {}", placeDto.getId());
        return placeDto;
    }

    @PostMapping("/multiple")
    public List<PlaceDto> createPlaces(@RequestBody @Valid List<PlaceCreateDto> placeCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        return placeService.createMultiple(placeCreateDto);
    }

    @PutMapping("/{id}")
    public PlaceDto updatePlace(@PathVariable Long id, @RequestBody @Valid PlaceDto placeUpdateDto,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        placeUpdateDto.setId(id);
        PlaceDto placeDto = placeService.updatePlace(placeUpdateDto);
        log.info("Place updated with ID: {}", placeDto.getId());
        return placeDto;
    }

    @DeleteMapping("/{id}")
    public void deletePlace(@PathVariable Long id) {
        log.info("Deleting place with ID: {}", id);
        placeService.deletePlace(id);
        log.info("Place deleted with ID: {}", id);
    }

    @GetMapping("/{placeId}")
    public PlaceDto getPlaceById(@PathVariable Long placeId) {
        log.info("Retrieving place with ID: {}", placeId);
        return placeService.getPlaceById(placeId);
    }

    @GetMapping
    public List<PlaceDto> getAllPlacesByUserId(@RequestParam Long userId) {
        log.info("Retrieving all places for user with ID: {}", userId);
        return placeService.getAllPlacesByUserId(userId);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
