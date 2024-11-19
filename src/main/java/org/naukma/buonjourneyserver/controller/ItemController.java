package org.naukma.buonjourneyserver.controller;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.ItemDto;
import org.naukma.buonjourneyserver.dto.createDto.ItemCreateDto;
import org.naukma.buonjourneyserver.dto.updateDto.UpdateItemNameDto;
import org.naukma.buonjourneyserver.dto.updateDto.UpdateItemPackedDto;
import org.naukma.buonjourneyserver.exceptions.ExceptionHelper;
import org.naukma.buonjourneyserver.service.IItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final IItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestBody @Valid ItemCreateDto itemCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        ItemDto itemDto = itemService.addItem(itemCreateDto);
        log.info("Item created with ID: {}", itemDto.getId());
        return itemDto;
    }

    @PutMapping("/{id}")
    public ItemDto updateItemName(@PathVariable Long id, @RequestBody @Valid UpdateItemNameDto itemUpdateDto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        itemUpdateDto.setId(id);
        ItemDto itemDto = itemService.updateItemName(itemUpdateDto);
        log.info("Item updated with ID: {}", itemDto.getId());
        return itemDto;
    }

    @PutMapping("/{id}/pack")
    public ItemDto updateItemPacked(@PathVariable Long id, @RequestBody @Valid UpdateItemPackedDto itemUpdateDto,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        itemUpdateDto.setId(id);
        ItemDto itemDto = itemService.updateItemPacked(itemUpdateDto);
        log.info("Item updated with ID: {}", itemDto.getId());
        return itemDto;
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        log.info("Deleting item with ID: {}", id);
        itemService.removeItem(id);
        log.info("Item deleted with ID: {}", id);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable Long itemId) {
        log.info("Retrieving item with ID: {}", itemId);
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<ItemDto> getAllByPackingListId(@RequestParam Long packingListId) {
        log.info("Retrieving all items for trip with ID: {}", packingListId);
        return itemService.getAllByByPackingListId(packingListId);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
