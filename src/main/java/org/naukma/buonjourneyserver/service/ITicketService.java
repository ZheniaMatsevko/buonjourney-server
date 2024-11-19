package org.naukma.buonjourneyserver.service;


import org.naukma.buonjourneyserver.dto.TicketDto;
import org.naukma.buonjourneyserver.dto.TripDto;
import org.naukma.buonjourneyserver.dto.createDto.TicketCreateDto;
import org.naukma.buonjourneyserver.dto.updateDto.UpdateTicketUsedDto;

import java.util.List;

public interface ITicketService {
    List<TicketDto> getAllByTripId(Long tripId);

    TicketDto createTicket(TicketCreateDto ticket);

    TicketDto createTicket(TicketDto ticket, TripDto tripDto);

    TicketDto updateTicket(TicketDto ticket);

    TicketDto updateTicketUsed(UpdateTicketUsedDto ticket);


    void deleteTicket(Long id);

    TicketDto getTicketById(Long id);

    List<TicketDto> createMultiple(List<TicketCreateDto> ticketCreateDtos);
}
