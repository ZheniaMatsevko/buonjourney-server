package org.naukma.buonjourneyserver.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.repository.ITicketRepository;
import org.naukma.buonjourneyserver.service.ITicketService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    private final ITicketRepository ticketRepository;

}
