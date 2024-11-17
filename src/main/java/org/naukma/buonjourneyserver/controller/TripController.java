package org.naukma.buonjourneyserver.controller;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.TripDto;
import org.naukma.buonjourneyserver.dto.createDto.TripCreateDto;
import org.naukma.buonjourneyserver.dto.updateDto.TripUpdateDto;
import org.naukma.buonjourneyserver.exceptions.ExceptionHelper;
import org.naukma.buonjourneyserver.service.ITripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {
    private final ITripService tripService;

    @PostMapping
    public TripDto createTrip(@RequestBody @Valid TripCreateDto tripCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        TripDto tripDto = tripService.createTrip(tripCreateDto);
        log.info("Trip created with ID: {}", tripDto.getId());
        return tripDto;
    }

    @PutMapping("/{id}")
    public TripDto updateTrip(@PathVariable Long id, @RequestBody @Valid TripUpdateDto tripUpdateDto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        tripUpdateDto.setId(id);
        TripDto tripDto = tripService.updateTrip(tripUpdateDto);
        log.info("Trip updated with ID: {}", tripDto.getId());
        return tripDto;
    }

    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        log.info("Deleting trip with ID: {}", id);
        tripService.deleteTrip(id);
        log.info("Trip deleted with ID: {}", id);
    }

    @GetMapping("/{tripId}")
    public TripDto getTripById(@PathVariable Long tripId) {
        log.info("Retrieving trip with ID: {}", tripId);
        return tripService.getTripById(tripId);
    }

    @GetMapping
    public List<TripDto> getAllTripsByUserId(@RequestParam Long userId) {
        log.info("Retrieving all trips for user with ID: {}", userId);
        return tripService.getAllByUserId(userId);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
