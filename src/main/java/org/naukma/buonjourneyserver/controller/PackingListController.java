package org.naukma.buonjourneyserver.controller;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.PackingListDto;
import org.naukma.buonjourneyserver.dto.createDto.PackingListCreateDto;
import org.naukma.buonjourneyserver.exceptions.ExceptionHelper;
import org.naukma.buonjourneyserver.service.IPackingListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/packing")
@RequiredArgsConstructor
public class PackingListController {

    private final IPackingListService packingListService;

    @PostMapping
    public PackingListDto createPackingList(@RequestBody @Valid PackingListCreateDto packingListCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        PackingListDto packingListDto = packingListService.createPackingList(packingListCreateDto);
        log.info("Packing list created with ID: {}", packingListDto.getId());
        return packingListDto;
    }

    @PutMapping("/{id}")
    public PackingListDto updatePackingList(@PathVariable Long id, @RequestBody @Valid PackingListDto packingListUpdateDto,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        packingListUpdateDto.setId(id);
        PackingListDto packingListDto = packingListService.updatePackingList(packingListUpdateDto);
        log.info("Packing list updated with ID: {}", packingListDto.getId());
        return packingListDto;
    }

    @DeleteMapping("/{id}")
    public void deletePackingList(@PathVariable Long id) {
        log.info("Deleting packing list with ID: {}", id);
        packingListService.deletePackingList(id);
        log.info("Packing list deleted with ID: {}", id);
    }

    @GetMapping("/{packingListId}")
    public PackingListDto getPackingListById(@PathVariable Long packingListId) {
        log.info("Retrieving packing list with ID: {}", packingListId);
        return packingListService.getPackingListById(packingListId);
    }

    @GetMapping
    public List<PackingListDto> getAllByTripId(@RequestParam Long tripId) {
        log.info("Retrieving all packing lists for trip with ID: {}", tripId);
        return packingListService.getAllByTripId(tripId);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
