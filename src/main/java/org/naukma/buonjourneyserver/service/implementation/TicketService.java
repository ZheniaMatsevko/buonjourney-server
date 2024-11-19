package org.naukma.buonjourneyserver.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.TicketDto;
import org.naukma.buonjourneyserver.dto.TripDto;
import org.naukma.buonjourneyserver.dto.createDto.TicketCreateDto;
import org.naukma.buonjourneyserver.dto.updateDto.UpdateTicketUsedDto;
import org.naukma.buonjourneyserver.entity.TicketEntity;
import org.naukma.buonjourneyserver.mapper.ITicketMapper;
import org.naukma.buonjourneyserver.mapper.ITripMapper;
import org.naukma.buonjourneyserver.repository.ITicketRepository;
import org.naukma.buonjourneyserver.service.ITicketService;
import org.naukma.buonjourneyserver.service.ITripService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    private final ITicketRepository ticketRepository;
    private final ITripService tripService;

    @Override
    public List<TicketDto> getAllByTripId(Long tripId) {
        List<TicketEntity> ticketEntities = ticketRepository.findAllByTripId(tripId);
        return ticketEntities.stream().map(ITicketMapper.INSTANCE::entityToDto).toList();
    }

    @Override
    public TicketDto createTicket(TicketCreateDto ticket) {
        log.info("Creating ticket");

        TripDto tripDto = tripService.getTripById(ticket.getTripId());

        TicketEntity ticketToCreate = ITicketMapper.INSTANCE.createDtoToEntity(ticket);
        ticketToCreate.setTrip(ITripMapper.INSTANCE.dtoToEntity(tripDto));

        TicketEntity createdTicket = ticketRepository.save(ticketToCreate);

        log.info("Ticket created successfully.");
        return ITicketMapper.INSTANCE.entityToDto(createdTicket);
    }

    @Override
    public TicketDto createTicket(TicketDto ticket, TripDto tripDto) {
        TicketEntity ticketToCreate = ITicketMapper.INSTANCE.dtoToEntity(ticket);
        ticketToCreate.setTrip(ITripMapper.INSTANCE.dtoToEntity(tripDto));

        TicketEntity createdTicket = ticketRepository.save(ticketToCreate);

        log.info("Ticket created successfully.");
        return ITicketMapper.INSTANCE.entityToDto(createdTicket);
    }

    @Transactional
    @Override
    public TicketDto updateTicket(TicketDto ticket) throws EntityNotFoundException {
        Optional<TicketEntity> optional = ticketRepository.findById(ticket.getId());
        if (optional.isPresent()) {
            TicketEntity ticketEntity = optional.get();

            ticketEntity.setTicketType(ticket.getTicketType() == null ? ticketEntity.getTicketType() : ticket.getTicketType());
            ticketEntity.setCaption(ticket.getCaption() == null ? ticketEntity.getCaption() : ticket.getCaption());
            ticketEntity.setFileUrl(ticket.getFileUrl() == null ? ticketEntity.getFileUrl() : ticket.getFileUrl());
            ticketEntity.setDateTime(ticket.getDateTime() == null ? ticketEntity.getDateTime() : ticket.getDateTime());

            TicketEntity editedTicket = ticketRepository.save(ticketEntity);
            log.info("Updated ticket with id " + editedTicket.getId());

            return ITicketMapper.INSTANCE.entityToDto(editedTicket);
        } else {
            throw new EntityNotFoundException("Ticket not found for editing");
        }
    }

    @Override
    public TicketDto updateTicketUsed(UpdateTicketUsedDto ticket) throws EntityNotFoundException {
        Optional<TicketEntity> optional = ticketRepository.findById(ticket.getId());
        if (optional.isPresent()) {
            TicketEntity ticketEntity = optional.get();
            ticketEntity.setUsed(ticket.isUsed());

            TicketEntity editedTicket = ticketRepository.save(ticketEntity);
            log.info("Updated ticket with id " + editedTicket.getId());

            return ITicketMapper.INSTANCE.entityToDto(editedTicket);
        } else {
            throw new EntityNotFoundException("Ticket not found for editing");
        }
    }

    @Override
    public void deleteTicket(Long id) throws EntityNotFoundException {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            log.info("Deleted ticket with ID: {}", id);
        } else {
            throw new EntityNotFoundException("Ticket not found for deletion");
        }
    }

    @Override
    public TicketDto getTicketById(Long id) throws EntityNotFoundException {
        TicketEntity ticket = ticketRepository.findById(id).orElse(null);
        if (ticket != null) {
            log.info("Retrieved ticket with ID: {}", ticket.getId());
        } else {
            log.warn("Ticket not found with ID: {}", id);
            throw new EntityNotFoundException("Ticket with id " + id + " not found");
        }
        return ITicketMapper.INSTANCE.entityToDto(ticket);
    }

    @Override
    public List<TicketDto> createMultiple(List<TicketCreateDto> ticketCreateDtos) {
        List<TicketDto> tickets = new ArrayList<>();
        for (TicketCreateDto ticketCreateDto : ticketCreateDtos) {
            tickets.add(this.createTicket(ticketCreateDto));
        }
        return tickets;
    }
}
