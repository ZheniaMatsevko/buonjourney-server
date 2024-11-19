package org.naukma.buonjourneyserver.controller;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.TicketDto;
import org.naukma.buonjourneyserver.dto.createDto.TicketCreateDto;
import org.naukma.buonjourneyserver.dto.updateDto.UpdateTicketUsedDto;
import org.naukma.buonjourneyserver.exceptions.ExceptionHelper;
import org.naukma.buonjourneyserver.service.ITicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final ITicketService ticketService;

    @PostMapping
    public TicketDto createTicket(@RequestBody @Valid TicketCreateDto ticketCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        TicketDto ticketDto = ticketService.createTicket(ticketCreateDto);
        log.info("Ticket created with ID: {}", ticketDto.getId());
        return ticketDto;
    }

    @PostMapping("/multiple")
    public List<TicketDto> createTickets(@RequestBody @Valid List<TicketCreateDto> ticketCreateDtos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        return ticketService.createMultiple(ticketCreateDtos);
    }

    @PutMapping("/{id}")
    public TicketDto updateTicket(@PathVariable Long id, @RequestBody @Valid TicketDto ticketDto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        ticketDto.setId(id);
        TicketDto ticket = ticketService.updateTicket(ticketDto);
        log.info("Ticket updated with ID: {}", ticket.getId());
        return ticket;
    }

    @PutMapping("/{id}/use")
    public TicketDto updateTicket(@PathVariable Long id, @RequestBody @Valid UpdateTicketUsedDto ticketDto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        ticketDto.setId(id);
        TicketDto ticket = ticketService.updateTicketUsed(ticketDto);
        log.info("Ticket used updated with ID: {}", ticket.getId());
        return ticket;
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        log.info("Deleting ticket with ID: {}", id);
        ticketService.deleteTicket(id);
        log.info("Ticket deleted with ID: {}", id);
    }

    @GetMapping("/{id}")
    public TicketDto getTicketById(@PathVariable Long id) {
        log.info("Retrieving ticket with ID: {}", id);
        return ticketService.getTicketById(id);
    }

    @GetMapping
    public List<TicketDto> getAllByTripId(@RequestParam Long tripId) {
        log.info("Retrieving all tickets for trip with ID: {}", tripId);
        return ticketService.getAllByTripId(tripId);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
